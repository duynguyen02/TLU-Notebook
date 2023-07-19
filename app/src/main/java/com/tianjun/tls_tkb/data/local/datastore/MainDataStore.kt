package com.tianjun.tls_tkb.data.local.datastore

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tianjun.tls_tkb.BuildConfig
import com.tianjun.tls_tkb.util.secure.SecureHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val secureHelper: SecureHelper
){

    class CanNotGetPreferencesException : Exception("CanNotGetPreferencesException")

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    companion object{
        const val PREFERENCES_NAME = "main_preferences"
        val SSL_KEY = stringPreferencesKey("ssl_key")
        val HOST_PORT = stringPreferencesKey("host_port")
        val HOST_NAME = stringPreferencesKey("host_name")
        val STUDENT_ID = stringPreferencesKey("student_id")
        val STUDENT_PASSWORD = stringPreferencesKey("student_password")
    }


    private suspend fun FlowCollector<Preferences>.emitEmpty(throwable: Throwable){
        if (throwable is IOException) {
            throwable.printStackTrace()
            emit(emptyPreferences())
        } else {
            throw throwable
        }
    }

    private fun <T>getFlowPreference(preferencesKey : Preferences.Key<T>, defaultValue : T?, processor : (suspend (T) -> T)?) : Flow<T> =
        context.dataStore.data
            .catch {
                emitEmpty(it)
            }
            .map {
                it[preferencesKey]?.run { processor?.invoke(this) ?: this } ?: defaultValue ?: throw CanNotGetPreferencesException()
            }

    val sslKey = getFlowPreference(SSL_KEY, null,null)
    val hostPort = getFlowPreference(HOST_PORT, BuildConfig.HOST_PORT,null)
    val hostName = getFlowPreference(HOST_NAME, BuildConfig.HOST_NAME,null)
    val studentId = getFlowPreference(STUDENT_ID, null,null)
    val studentPassword = getFlowPreference(STUDENT_PASSWORD, null){
        secureHelper.decrypt(it)
    }

}