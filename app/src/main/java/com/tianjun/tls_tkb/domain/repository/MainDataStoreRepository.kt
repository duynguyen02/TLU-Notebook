package com.tianjun.tls_tkb.domain.repository

import kotlinx.coroutines.flow.Flow

interface MainDataStoreRepository {
    fun getHostPort() : Flow<String?>
    suspend fun setHostPort(value : String)

    fun getHostName() : Flow<String?>
    suspend fun setHostName(value : String)
    fun getStudentID() : Flow<String?>
    suspend fun setStudentID(value: String)
    fun getStudentPassword() : Flow<String?>
    suspend fun setStudentPassword(value: String)
}