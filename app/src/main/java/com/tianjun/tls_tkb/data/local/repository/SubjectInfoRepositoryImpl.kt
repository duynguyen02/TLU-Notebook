package com.tianjun.tls_tkb.data.local.repository

import com.tianjun.tls_tkb.data.local.dao.SubjectInfoDao
import com.tianjun.tls_tkb.data.local.entity.subject.SubjectInfoEnt
import com.tianjun.tls_tkb.domain.model.SubjectInfo
import com.tianjun.tls_tkb.domain.repository.SubjectInfoRepository
import javax.inject.Inject

class SubjectInfoRepositoryImpl @Inject constructor(
    private val subjectInfoDao: SubjectInfoDao
) : SubjectInfoRepository {

    override suspend fun addSubjectsInfo(subjectsInfo: List<SubjectInfo>) = subjectInfoDao.addSubjectsInfo(subjectsInfo.map {
        SubjectInfoEnt.subjectInfoToEnt(it)
    })

    override suspend fun deleteAllSubjects() = subjectInfoDao.deleteAllSubjects()

}