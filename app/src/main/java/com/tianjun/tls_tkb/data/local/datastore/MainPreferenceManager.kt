package com.tianjun.tls_tkb.data.local.datastore

import android.content.SharedPreferences
import com.tianjun.tls_tkb.BuildConfig
import com.tianjun.tls_tkb.module.StringSharedPreferencesLiveData
import javax.inject.Inject

class MainPreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    enum class Key(val value : String){
        HOST_PORT("host_port"),
        HOST_NAME("host_name")
    }

    fun save(key : Key, value : String){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key.value, value)
        editor.apply()
    }

    val hostNameLiveData = StringSharedPreferencesLiveData(sharedPreferences, Key.HOST_NAME.value, BuildConfig.HOST_NAME)
    val hostName = hostNameLiveData.getUnLiveValue()

    val hostPortLiveData = StringSharedPreferencesLiveData(sharedPreferences, Key.HOST_PORT.value, BuildConfig.HOST_PORT)
    val hostPort = hostPortLiveData.getUnLiveValue()

}