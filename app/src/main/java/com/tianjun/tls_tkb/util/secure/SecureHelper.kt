package com.tianjun.tls_tkb.util.secure

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.tianjun.tls_tkb.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureHelper @Inject constructor(
    private val keyGenParameterSpec: KeyGenParameterSpec
) {

    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private suspend fun generateOrFetchKey(): SecretKey =  withContext(Dispatchers.IO) {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        if (!keyStore.containsAlias(BuildConfig.APP_KEY_ALIAS)) {
            generateKey()
        }
        val secretKeyEntry = keyStore.getEntry(BuildConfig.APP_KEY_ALIAS, null) as KeyStore.SecretKeyEntry
        secretKeyEntry.secretKey
    }

    suspend fun encrypt(str: String): String {
        val secretKey = generateOrFetchKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(str.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    suspend fun decrypt(encryptedStr: String): String {
        val secretKey = generateOrFetchKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val encryptedBytes = Base64.decode(encryptedStr, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}