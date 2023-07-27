package com.tianjun.tls_tkb.data.remote.repository

import com.tianjun.tls_tkb.data.remote.api.ServerService
import com.tianjun.tls_tkb.di.ApplicationModule
import com.tianjun.tls_tkb.di.WebServiceModule
import com.tianjun.tls_tkb.domain.repository.ServerInfoRepository
import com.tianjun.tls_tkb.util.ssl.SSLUtil
import javax.inject.Inject
import retrofit2.converter.scalars.ScalarsConverterFactory

class ServerInfoRepositoryImpl @Inject constructor(
    private val baseUrl: String
) : ServerInfoRepository{

    private var serverService : ServerService = WebServiceModule.createRetrofit(
        baseUrl,
        ScalarsConverterFactory.create(),
        null
    ).create(ServerService::class.java)

    override suspend fun getServerPublicSSLCertificate(): String? {
        return try {
            val certificate = SSLUtil.getSSLPublicKey(baseUrl)
            SSLUtil.formatCertificateAsString(certificate)
        } catch (e: Exception){
            null
        }
    }

    override suspend fun getServerPort(): String?{
        try {
            val response = serverService.getServerConfigFile()
            if (response.isSuccessful) {
                return response.body()?.let {
                    val regex = "Education.PORT\\s*=\\s*'(.*)';".toRegex()
                    regex.find(it)?.groups?.get(1)?.value
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}