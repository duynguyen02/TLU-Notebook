package com.tianjun.tls_tkb.domain.repository

import kotlinx.coroutines.flow.Flow

interface MainDataStoreRepository {
    fun getSSLKey() : Flow<String>
    fun getHostPort() : Flow<String>
    fun getHostName() : Flow<String>
    fun getStudentID() : Flow<String>
    fun getStudentPassword() : Flow<String>
}