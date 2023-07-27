package com.tianjun.tls_tkb.domain.repository

import android.content.SharedPreferences
import com.tianjun.tls_tkb.module.SharedPreferencesLiveData

interface MainPreferenceRepository {
    fun getHostNameAsLiveData() : SharedPreferencesLiveData<String>
    fun getHostName() : String
    suspend fun setHostName(hostname : String)
    fun getHostPortAsLiveData() : SharedPreferencesLiveData<String>
    fun getHostPort() : String
    suspend fun setHostPort(hostPort : String)
}