package com.tianjun.tls_tkb.di

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.room.Room
import com.tianjun.tls_tkb.BuildConfig
import com.tianjun.tls_tkb.data.local.repository.MainDataStoreRepositoryImpl
import com.tianjun.tls_tkb.data.local.repository.RoomRepositoryImpl
import com.tianjun.tls_tkb.data.remote.api.ServerService
import com.tianjun.tls_tkb.data.remote.api.StudentService
import com.tianjun.tls_tkb.data.remote.repository.ServerInfoRepositoryImpl
import com.tianjun.tls_tkb.domain.repository.MainDataStoreRepository
import com.tianjun.tls_tkb.domain.repository.RoomRepository
import com.tianjun.tls_tkb.domain.repository.ServerInfoRepository
import com.tianjun.tls_tkb.module.ApplicationRoomDatabase
import com.tianjun.tls_tkb.util.ssl.SSLUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideKeySpec() = KeyGenParameterSpec.Builder(
        BuildConfig.APP_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .build()


}