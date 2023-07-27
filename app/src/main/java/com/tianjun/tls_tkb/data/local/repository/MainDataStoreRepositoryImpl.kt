package com.tianjun.tls_tkb.data.local.repository

import com.tianjun.tls_tkb.data.local.datastore.MainDataStore
import com.tianjun.tls_tkb.domain.repository.MainDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDataStoreRepositoryImpl @Inject constructor(
    private val mainDataStore: MainDataStore
) : MainDataStoreRepository {
    override fun getHostPort(): Flow<String?> = mainDataStore.hostPort
    override suspend fun setHostPort(value: String) = mainDataStore.saveValue(MainDataStore.HOST_PORT, value)

    override fun getHostName(): Flow<String?> = mainDataStore.hostName
    override suspend fun setHostName(value: String) = mainDataStore.saveValue(MainDataStore.HOST_NAME, value)

    override fun getStudentID(): Flow<String?> = mainDataStore.studentId
    override suspend fun setStudentID(value: String) = mainDataStore.saveValue(MainDataStore.STUDENT_ID, value)

    override fun getStudentPassword(): Flow<String?> = mainDataStore.studentPassword
    override suspend fun setStudentPassword(value: String) = mainDataStore.saveValue(MainDataStore.STUDENT_PASSWORD, value)

}