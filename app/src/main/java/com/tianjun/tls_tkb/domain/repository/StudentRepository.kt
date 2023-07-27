package com.tianjun.tls_tkb.domain.repository

import com.tianjun.tls_tkb.domain.model.Login
import com.tianjun.tls_tkb.domain.model.LoginToken
import com.tianjun.tls_tkb.domain.model.SemesterInfo
import com.tianjun.tls_tkb.domain.model.SubjectInfo
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    suspend fun login(login: Login) : LoginToken
    suspend fun getSemesterInfo(token: LoginToken) : SemesterInfo
    suspend fun getSubjectsInfo(token: LoginToken, semesterInfo : SemesterInfo) : List<SubjectInfo>
}