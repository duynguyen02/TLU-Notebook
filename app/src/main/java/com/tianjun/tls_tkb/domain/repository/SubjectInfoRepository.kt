package com.tianjun.tls_tkb.domain.repository

import com.tianjun.tls_tkb.domain.model.SubjectInfo

interface SubjectInfoRepository {
    suspend fun addSubjectsInfo(subjectsInfo : List<SubjectInfo>)
    suspend fun deleteAllSubjects()
}