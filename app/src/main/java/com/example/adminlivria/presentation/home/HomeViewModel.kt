package com.example.adminlivria.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.adminlivria.domain.profilecontext.entity.AdminUser

data class HomeUiState(
    val user: AdminUser = AdminUser.mock(),

)

class HomeViewModel : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set


}