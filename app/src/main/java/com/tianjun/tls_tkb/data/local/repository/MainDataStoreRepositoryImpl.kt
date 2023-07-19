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

    override fun getSSLKey(): Flow<String> = mainDataStore.sslKey

    override fun getHostPort(): Flow<String> = mainDataStore.hostPort

    override fun getHostName(): Flow<String> = mainDataStore.hostName

    override fun getStudentID(): Flow<String> = mainDataStore.studentId

    override fun getStudentPassword(): Flow<String> = mainDataStore.studentPassword
}