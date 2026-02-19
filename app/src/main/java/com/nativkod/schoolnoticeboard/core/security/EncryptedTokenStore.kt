package com.nativkod.schoolnoticeboard.core.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedTokenStore(context: Context) : TokenStore {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    override fun setToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun clear() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }

    private companion object {
        private const val KEY_TOKEN = "session_token"
    }
}
