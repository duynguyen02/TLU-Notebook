package com.tianjun.tls_tkb.di

import com.tianjun.tls_tkb.BuildConfig
import com.tianjun.tls_tkb.data.remote.api.ServerService
import com.tianjun.tls_tkb.data.remote.api.StudentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object WebServiceModule {

    fun createRetrofit(
        baseUrl : String,
        converterFactory : Converter.Factory,
        client : OkHttpClient?
    ): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)

        client?.let { retrofitBuilder.client(it) }

        return retrofitBuilder.build()
    }

    @Provides
    @Singleton
    fun provideServerService(@Qualifiers.RawResponseRetrofitConverter retrofit: Retrofit): ServerService =
        retrofit.create(ServerService::class.java)

    @Provides
    @Singleton
    fun provideSSLClient(serverKey : String) : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val inputStream = serverKey.byteInputStream()
        val cert = certificateFactory.generateCertificate(inputStream)
        keyStore.setCertificateEntry("certificate_alias", cert)
        trustManagerFactory.init(keyStore)
        val sslContext = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName)
        sslContext.init(null, trustManagerFactory.trustManagers, null)

        return OkHttpClient.Builder().apply {
            sslSocketFactory(sslContext.socketFactory, trustManagerFactory.trustManagers[0] as X509TrustManager)
            if (BuildConfig.DEBUG){
                addInterceptor(loggingInterceptor)
            }
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideStudentService(@Qualifiers.JsonResponseRetrofitConverter retrofit: Retrofit): StudentService =
        retrofit.create(StudentService::class.java)
}