package com.tianjun.tls_tkb.data.local.repository

import android.content.SharedPreferences
import com.tianjun.tls_tkb.data.local.datastore.MainPreferenceManager
import com.tianjun.tls_tkb.domain.repository.MainPreferenceRepository
import com.tianjun.tls_tkb.module.SharedPreferencesLiveData
import javax.inject.Inject

class MainPreferenceRepositoryImpl @Inject constructor(
    private val mainPreferenceManager: MainPreferenceManager
) : MainPreferenceRepository {

    override fun getHostNameAsLiveData(): SharedPreferencesLiveData<String> = mainPreferenceManager.hostNameLiveData

    override fun getHostName(): String = mainPreferenceManager.hostName

    override suspend fun setHostName(hostname: String) {
        mainPreferenceManager.save(MainPreferenceManager.Key.HOST_NAME, hostname)
    }

    override fun getHostPortAsLiveData(): SharedPreferencesLiveData<String> = mainPreferenceManager.hostPortLiveData

    override fun getHostPort(): String = mainPreferenceManager.hostPort

    override suspend fun setHostPort(hostPort: String) {
        mainPreferenceManager.save(MainPreferenceManager.Key.HOST_PORT, hostPort)
    }

}