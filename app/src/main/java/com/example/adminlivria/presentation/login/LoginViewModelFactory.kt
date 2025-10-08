package com.example.adminlivria.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.data.remote.AuthService
import java.lang.IllegalArgumentException

class LoginViewModelFactory(
    private val authService: AuthService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}