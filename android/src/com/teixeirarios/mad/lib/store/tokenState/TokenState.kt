package com.teixeirarios.mad.lib.store.tokenState

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.auth0.android.jwt.JWT

object TokenState {

    private const val PREFS_NAME = "auth_prefs"
    private const val TOKEN_KEY = "jwt_token"

    private val _tokenFlow = MutableStateFlow<String?>(null)
    val tokenFlow: StateFlow<String?> = _tokenFlow

    fun init(context: Context) {
        val token = getSharedPreferences(context).getString(TOKEN_KEY, null) ?: return
        if (this.isTokenExpired(token)) return

        _tokenFlow.value = token
    }

    private fun getSharedPreferences(context: Context) =
        EncryptedSharedPreferences.create(
            PREFS_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
        _tokenFlow.value = token
    }

    fun getToken(): StateFlow<String?> = tokenFlow

    fun clearToken(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
        _tokenFlow.value = null
    }

    fun isTokenExpired(token: String): Boolean {
        return try {
            val jwt = JWT(token)
            return jwt.isExpired(3600) // Verifica se o token está expirado com uma margem de 1h
        } catch (e: Exception) {
            true // Se o token não for válido, considere-o expirado
        }
    }
}