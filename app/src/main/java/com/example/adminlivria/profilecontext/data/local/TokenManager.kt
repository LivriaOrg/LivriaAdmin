package com.example.adminlivria.profilecontext.data.local

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "livria_prefs"
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_ADMIN_ID = "admin_id"
    }

    fun saveAuthData(token: String, adminId: Int) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putInt(KEY_ADMIN_ID, adminId)
            apply()
        }
    }


    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }


    fun getAdminId(): Int {

        return prefs.getInt(KEY_ADMIN_ID, 0)
    }


    fun clearAuthData() {
        prefs.edit().clear().apply()
    }
}