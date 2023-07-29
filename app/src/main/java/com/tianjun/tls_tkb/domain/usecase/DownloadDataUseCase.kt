package com.tianjun.tls_tkb.domain.usecase

import com.tianjun.tls_tkb.data.remote.repository.ServerInfoRepositoryImpl
import com.tianjun.tls_tkb.data.remote.repository.StudentRepositoryImpl
import com.tianjun.tls_tkb.domain.model.Login
import com.tianjun.tls_tkb.domain.model.LoginToken
import com.tianjun.tls_tkb.domain.repository.MainPreferenceRepository
import com.tianjun.tls_tkb.domain.repository.RoomRepository
import com.tianjun.tls_tkb.domain.repository.ServerInfoRepository
import com.tianjun.tls_tkb.domain.repository.SubjectInfoRepository
import com.tianjun.tls_tkb.domain.repository.TimetableRepository
import com.tianjun.tls_tkb.util.ssl.SSLUtil
import com.tianjun.tls_tkb.util.view.Result
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DownloadDataUseCase @Inject constructor(
    private val mainPreferenceRepository: MainPreferenceRepository,
    private val subjectInfoRepository: SubjectInfoRepository,
    private val timetableRepository: TimetableRepository
) {

    class AuthenticationException : Exception("Không thể xác thực chứng chỉ từ máy chủ!")

    private lateinit var serverInfoRepository: ServerInfoRepository


    operator fun invoke(login : Login) = flow {

        try {
            val hostname = mainPreferenceRepository.getHostNameAsLiveData().value
            val hostPort = mainPreferenceRepository.getHostPortAsLiveData().value

            serverInfoRepository = ServerInfoRepositoryImpl(hostname!!)

            var sslKey: String? = null
            if (hostname.startsWith("https://")) {
                emit(Result.Loading("Đang xác thực chứng chỉ..."))
                sslKey = serverInfoRepository.getServerPublicSSLCertificate()
                if (sslKey == null) {
                    throw AuthenticationException()
                }
            }

            val studentRepository = StudentRepositoryImpl(hostname, hostPort!!,
                sslKey?.let { SSLUtil.convertCertificateStringToCertificate(it) })

            emit(Result.Loading("Đang đăng nhập..."))
            val token = studentRepository.login(login)

            if (token.accessToken.isEmpty()) {
                throw Exception("Không thể đăng nhập, vui lòng kiểm tra MSSV và mật khẩu!")
            }

            emit(Result.Loading("Đang xác định học kỳ..."))

            val semesterInfo = studentRepository.getSemesterInfo(token)

            if (semesterInfo.id == null){
                throw Exception("Không thể xác định học kỳ, vui lòng thử lại!")
            }

            emit(Result.Loading("Đang tải chờ xíu nha..."))

            val subjectsInfo = studentRepository.getSubjectsInfo(token, semesterInfo)

            subjectInfoRepository.deleteAllSubjects()
            timetableRepository.deleteAllTimetables()

            subjectInfoRepository.addSubjectsInfo(subjectsInfo)
            timetableRepository.addTimetables(subjectsInfo.flatMap { it.timetables ?: emptyList() })


            val resultString = buildString {
                appendLine("Tải thành công!")
                appendLine("Học kỳ: ${semesterInfo.semesterName}")
                appendLine("Số môn học: ${subjectsInfo.size}")
                subjectsInfo.forEach { subjectInfo ->
                    appendLine(subjectInfo.subjectName)
                }
            }
            emit(Result.Success(resultString))
        }
        catch (e : Exception){
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }


    }
}