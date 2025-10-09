package com.example.adminlivria.searchcontext.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.profilecontext.data.remote.UserAdminService

class HomeViewModelFactory(
    private val userAdminService: UserAdminService,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userAdminService, tokenManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}