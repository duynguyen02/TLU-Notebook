package com.tianjun.tls_tkb.module

import android.content.SharedPreferences
import javax.inject.Inject

class StringSharedPreferencesLiveData @Inject constructor(
    sharedPreferences: SharedPreferences,
    key : String,
    defaultValue : String
) : SharedPreferencesLiveData<String>(
    sharedPreferences,
    key,
    defaultValue
) {
    override fun getValueFromPreferences(key: String, defValue: String): String = sharedPreferences.getString(key, defValue)!!
}