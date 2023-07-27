package com.tianjun.tls_tkb.module

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData
import javax.inject.Inject


abstract class SharedPreferencesLiveData<T>(
    protected val sharedPreferences: SharedPreferences,
    private val key : String,
    private val defaultValue : T
) : LiveData<T>(){
    abstract fun getValueFromPreferences(key: String, defValue: T): T
    fun getUnLiveValue() = getValueFromPreferences(key, defaultValue)

    private val preferenceChangeListener =
        OnSharedPreferenceChangeListener { _, key ->
            if (this@SharedPreferencesLiveData.key == key) {
                value = getValueFromPreferences(key, defaultValue)
            }
        }

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defaultValue)
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}