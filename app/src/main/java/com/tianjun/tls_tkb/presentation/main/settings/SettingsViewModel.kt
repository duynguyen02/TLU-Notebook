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
import com.tianjun.tls_tkb.domain.usecase.DeleteAllDataUseCase
import com.tianjun.tls_tkb.domain.usecase.DownloadDataUseCase
import com.tianjun.tls_tkb.util.ssl.SSLUtil
import com.tianjun.tls_tkb.util.view.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainPreferenceRepository: MainPreferenceRepository,
    private val downloadDataUseCase: DownloadDataUseCase,
    private val deleteAllDataUseCase: DeleteAllDataUseCase
) : ViewModel() {

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

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
            deleteAllDataUseCase().collect{
                _uiState.postValue(it)
            }
        }
    }

    fun downloadData(login: Login) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadDataUseCase(login).collect{
                _uiState.postValue(it)
            }
        }

    }
}