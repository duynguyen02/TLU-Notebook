package com.tianjun.tls_tkb.data.remote.repository

import com.tianjun.tls_tkb.data.remote.api.StudentService
import com.tianjun.tls_tkb.data.remote.mapper.login.LoginDto
import com.tianjun.tls_tkb.data.remote.mapper.login.LoginTokenDto
import com.tianjun.tls_tkb.data.remote.mapper.semester.SemesterInfoDto
import com.tianjun.tls_tkb.data.remote.mapper.subject.SubjectInfoDto
import com.tianjun.tls_tkb.di.ApplicationModule
import com.tianjun.tls_tkb.di.WebServiceModule
import com.tianjun.tls_tkb.domain.model.Login
import com.tianjun.tls_tkb.domain.model.LoginToken
import com.tianjun.tls_tkb.domain.model.SemesterInfo
import com.tianjun.tls_tkb.domain.model.SubjectInfo
import com.tianjun.tls_tkb.domain.repository.StudentRepository
import com.tianjun.tls_tkb.util.network.NetworkHelper
import com.tianjun.tls_tkb.util.ssl.SSLUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.Certificate
import kotlin.jvm.Throws

class StudentRepositoryImpl(
    private val hostname: String,
    private val hostPort: String,
    private val certificate: Certificate?
) : StudentRepository {

    class CanNotConnectToServer : Exception("Không thể kết nối đến máy chủ!")

    private var retrofit: Retrofit = WebServiceModule.createRetrofit(
        "${hostname}:${hostPort}",
        GsonConverterFactory.create(),
        certificate?.let {
            WebServiceModule.provideSSLClient(
                SSLUtil.formatCertificateAsString(it)
            )
        }
    )
    private var studentService = retrofit.create(StudentService::class.java)

    @Throws(CanNotConnectToServer::class)
    override suspend fun login(login: Login): LoginToken {
        val loginRes = studentService.login(LoginDto.loginToDto(login))
        return if (loginRes.isSuccessful && loginRes.body() != null) {
            LoginTokenDto.dtoToLoginToken(loginRes.body()!!)
        } else {
            throw CanNotConnectToServer()
        }
    }

    @Throws(CanNotConnectToServer::class)
    override suspend fun getSemesterInfo(token: LoginToken): SemesterInfo {
        val semesterRes = studentService.getSemesterInfo(
            "Bearer ${token.accessToken}",
            hostname,
            hostname
        )
        return if (semesterRes.isSuccessful && semesterRes.body() != null) {
            SemesterInfoDto.dtoToSemesterInfo(semesterRes.body()!!)
        } else {
            throw CanNotConnectToServer()
        }
    }

    override suspend fun getSubjectsInfo(
        token: LoginToken,
        semesterInfo: SemesterInfo
    ): List<SubjectInfo> {
        val subjectInfoRes = studentService.getSubjects(
            semesterInfo.id.toString(),
            "Bearer ${token.accessToken}",
            hostname,
            hostname
        )
        return if (subjectInfoRes.isSuccessful && subjectInfoRes.body() != null) {
            subjectInfoRes.body()!!.map { SubjectInfoDto.dtoToSubjectInfo(it) }
        } else {
            throw CanNotConnectToServer()
        }
    }
}