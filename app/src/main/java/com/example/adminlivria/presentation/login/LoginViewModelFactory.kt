package com.example.adminlivria.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.data.local.TokenManager
import com.example.adminlivria.data.remote.AuthService
import java.lang.IllegalArgumentException

class LoginViewModelFactory(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authService, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
