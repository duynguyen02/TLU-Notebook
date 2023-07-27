package com.tianjun.tls_tkb.domain.repository

import kotlinx.coroutines.flow.Flow
import com.tianjun.tls_tkb.util.view.Result

interface ServerInfoRepository {
    suspend fun getServerPublicSSLCertificate() : String?
    suspend fun getServerPort() : String?
}