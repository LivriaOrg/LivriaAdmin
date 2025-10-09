package com.example.adminlivria.profilecontext.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.profilecontext.domain.AdminUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SettingsUiState(
    val user: AdminUser = AdminUser.mock(),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val isProfileTabSelected: Boolean = true,
    val securityPin: String = "",
    val receiveNotifications: Boolean = true,
    val receiveEmailAlerts: Boolean = true,
    val autoSaveEnabled: Boolean = false,
)

class SettingsViewModel : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    fun updateField(field: String, value: String) {
        val updatedUser = when (field) {
            "fullName" -> uiState.user.copy(fullName = value)
            "username" -> uiState.user.copy(username = value)
            "email" -> uiState.user.copy(email = value)
            else -> uiState.user
        }
        val newPin = if (field == "securityPin") value else uiState.securityPin
        uiState = uiState.copy(user = updatedUser, securityPin = newPin, saveSuccess = false)
    }

    fun setTab(isProfile: Boolean) {
        uiState = uiState.copy(isProfileTabSelected = isProfile)
    }

    fun saveChanges() {
        uiState = uiState.copy(isSaving = true)

        viewModelScope.launch {
            // Simulación de la llamada al repositorio
            delay(500) // Simula la latencia de la red de 0.5 segundos

            // 1. Finaliza el guardado y establece el éxito
            uiState = uiState.copy(isSaving = false, saveSuccess = true)

            // 2. TEMPORIZADOR: Después de 3 segundos, oculta el mensaje
            delay(3000) // Espera 3 segundos
            uiState = uiState.copy(saveSuccess = false)
        }
    }


    fun updateApplicationSetting(setting: String, isEnabled: Boolean) {
        uiState = when (setting) {
            "notifications" -> uiState.copy(receiveNotifications = isEnabled, saveSuccess = false)
            "emailAlerts" -> uiState.copy(receiveEmailAlerts = isEnabled, saveSuccess = false)
            "autoSave" -> uiState.copy(autoSaveEnabled = isEnabled, saveSuccess = false)
            else -> uiState
        }
    }

    fun logout() {
        println("Cierre de sesión simulado")
    }
}
