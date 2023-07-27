package com.tianjun.tls_tkb.presentation.main.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tianjun.tls_tkb.BuildConfig
import com.tianjun.tls_tkb.data.remote.api.StudentService
import com.tianjun.tls_tkb.data.remote.mapper.login.LoginDto
import com.tianjun.tls_tkb.data.remote.mapper.login.LoginTokenDto
import com.tianjun.tls_tkb.data.remote.mapper.semester.SemesterInfoDto
import com.tianjun.tls_tkb.data.remote.repository.ServerInfoRepositoryImpl
import com.tianjun.tls_tkb.data.remote.repository.StudentRepositoryImpl
import com.tianjun.tls_tkb.di.ApplicationModule
import com.tianjun.tls_tkb.domain.model.Login
import com.tianjun.tls_tkb.domain.repository.MainPreferenceRepository
import com.tianjun.tls_tkb.domain.repository.ServerInfoRepository
import com.tianjun.tls_tkb.domain.repository.SubjectInfoRepository
import com.tianjun.tls_tkb.domain.repository.TimetableRepository
import com.tianjun.tls_tkb.util.ssl.SSLUtil
import com.tianjun.tls_tkb.util.view.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainPreferenceRepository: MainPreferenceRepository,
    private val subjectInfoRepository: SubjectInfoRepository,
    private val timetableRepository: TimetableRepository
) : ViewModel() {

    class AuthenticationException : Exception("Không thể xác thực chứng chỉ từ máy chủ!")

    private var serverInfoRepository: ServerInfoRepository =
        ServerInfoRepositoryImpl(mainPreferenceRepository.getHostName())

    private val _uiState = MutableLiveData<Result>()
    val uiState: LiveData<Result>
        get() = _uiState


    fun setNothingState() {
        _uiState.postValue(Result.Nothing)
    }

    fun getHostName() = mainPreferenceRepository.getHostNameAsLiveData()
    fun setHostName(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainPreferenceRepository.setHostName(value)
            serverInfoRepository = ServerInfoRepositoryImpl(value)
            detectPort()
        }
    }

    fun setDefaultHostName() {
        setHostName(BuildConfig.HOST_NAME)
    }

    fun getHostPort() = mainPreferenceRepository.getHostPortAsLiveData()
    fun setHostPort(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainPreferenceRepository.setHostPort(value)
        }
    }

    fun setDefaultHostPort() {
        setHostPort(BuildConfig.HOST_PORT)
    }

    fun detectPort() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.postValue(Result.Loading("Đang tìm cổng..."))
            serverInfoRepository.getServerPort()?.let {
                setHostPort(it)
                _uiState.postValue(Result.Success("Đã tìm được cổng: $it"))
            } ?: _uiState.postValue(Result.Error("Không tìm thấy cổng!"))
        }
    }

    /**
     * Function to download data by performing a login and making a network request to the server.
     * The function is responsible for handling the login process and API request to fetch data from the server.
     *
     * It first retrieves the hostname from the mainPreferenceRepository to construct the server URL.
     * If the hostname starts with "https://", it attempts to authenticate the SSL certificate from the server.
     *
     * The function uses the ApplicationModule to create a Retrofit instance with the constructed URL and other configurations.
     * It then creates a StudentService interface to interact with the server API using Retrofit.
     *
     * Inside the function, it starts by updating the UI state to show a loading message to the user.
     * The login request is made to the server using the studentService.login() method.
     * If the login is successful, the server response is printed, and the UI state is updated to show a success message.
     * If any error occurs during the process, an error message is displayed to the user.
     *
     * Note: The function runs on the IO dispatcher to perform the network operations asynchronously.
     *
     * @param login The Login object containing the username and password for authentication.
     * @see mainPreferenceRepository: The repository to access and manage app preferences, including the hostname.
     * @see serverInfoRepository: The repository responsible for handling server-related information.
     * @see ApplicationModule: A module containing functions for creating Retrofit instances and SSL client configuration.
     * @see StudentService: The Retrofit service interface to define API endpoints and request methods.
     * @see Result: A sealed class used to represent the different states of the UI during the data download process.
     *             It includes Loading, Success, and Error states.
     */
    fun downloadData(login: Login) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val hostname = mainPreferenceRepository.getHostNameAsLiveData().value
                val hostPort = mainPreferenceRepository.getHostPortAsLiveData().value

                var sslKey: String? = null
                if (hostname!!.startsWith("https://")) {
                    _uiState.postValue(Result.Loading("Đang xác thực chứng chỉ..."))
                    sslKey = serverInfoRepository.getServerPublicSSLCertificate()
                    if (sslKey == null) {
                        throw AuthenticationException()
                    }
                }

                val studentRepository = StudentRepositoryImpl(hostname, hostPort!!,
                    sslKey?.let { SSLUtil.convertCertificateStringToCertificate(it) })


                _uiState.postValue(Result.Loading("Đang đăng nhập..."))
                val token = studentRepository.login(login)

                if (token.accessToken.isEmpty()) {
                    throw Exception("Không thể đăng nhập, vui lòng kiểm tra MSSV và mật khẩu!")
                }

                _uiState.postValue(Result.Loading("Đang xác định học kỳ..."))

                val semesterInfo = studentRepository.getSemesterInfo(token)

                if (semesterInfo.id == null){
                    throw Exception("Không thể xác định học kỳ, vui lòng thử lại!")
                }

                _uiState.postValue(Result.Loading("Đang tải chờ xíu nha..."))

                val subjectsInfo = studentRepository.getSubjectsInfo(token, semesterInfo)

                subjectInfoRepository.addSubjectsInfo(subjectsInfo)

                subjectsInfo.flatMap { it.timetables ?: emptyList() }
                    .let { timetableRepository.addTimetables(it) }

                val resultString = buildString {
                    appendLine("Tải thành công!")
                    appendLine("Học kỳ: ${semesterInfo.semesterName}")
                    appendLine("Số môn học: ${subjectsInfo.size}")
                    subjectsInfo.forEach { subjectInfo ->
                        appendLine(subjectInfo.subjectName)
                    }
                }

                _uiState.postValue(Result.Success(resultString.toString()))


            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.postValue(Result.Error(e.message.toString()))
            }


        }

    }

}