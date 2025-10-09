package com.example.adminlivria.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.data.remote.UserAdminService
import java.lang.IllegalArgumentException


class SettingsViewModelFactory(
    private val userAdminService: UserAdminService,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(userAdminService, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}