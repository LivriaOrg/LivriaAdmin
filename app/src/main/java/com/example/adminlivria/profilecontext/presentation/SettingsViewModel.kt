package com.example.adminlivria.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.data.remote.UserAdminService
import com.example.adminlivria.data.model.UserAdminDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


data class SettingsUiState(
    val display: String = "",
    val username: String = "",
    val email: String = "",
    val securityPin: String = "",
    val capital: Double = 0.0,
    val isLoading: Boolean = false,
    val initialLoadError: String? = null,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val saveError: String? = null,

    val isProfileTabSelected: Boolean = true,
    val receiveNotifications: Boolean = true,
    val receiveEmailAlerts: Boolean = true,
    val autoSaveEnabled: Boolean = false,
)

class SettingsViewModel(
    private val userAdminService: UserAdminService,
    private val tokenManager: TokenManager
) : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    private val adminId: Int = tokenManager.getAdminId()

    init {
        loadAdminData()
    }


    fun updateField(field: String, value: String) {
        val updatedState = when (field) {
            "fullName" -> uiState.copy(display = value)
            "username" -> uiState.copy(username = value)
            "email" -> uiState.copy(email = value)
            "securityPin" -> uiState.copy(securityPin = value)
            else -> uiState
        }
        uiState = updatedState.copy(saveSuccess = false, saveError = null)
    }

    fun setTab(isProfile: Boolean) {
        uiState = uiState.copy(isProfileTabSelected = isProfile)
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
        tokenManager.clearAuthData()
        println("Cierre de sesión completado.")
    }


    fun loadAdminData() {
        if (adminId == 0) {
            uiState = uiState.copy(initialLoadError = "Error: Sesión no válida. Por favor, vuelva a iniciar sesión.", isLoading = false)
            return
        }

        uiState = uiState.copy(isLoading = true, initialLoadError = null)

        viewModelScope.launch {
            try {
                val response = userAdminService.getUserAdminData()

                if (response.isSuccessful) {
                    val adminList = response.body()
                    val adminDto = adminList?.firstOrNull()
                    adminDto?.let {
                        uiState = uiState.copy(
                            display = it.display,
                            username = it.username,
                            email = it.email,
                            securityPin = it.securityPin,
                            capital = adminDto.capital,
                            isLoading = false
                        )
                    }
                } else {
                    uiState = uiState.copy(
                        initialLoadError = "No se pudieron cargar los datos. (Error ${response.code()})",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                val errorMsg = when (e) {
                    is HttpException -> "Error de servidor al cargar: ${e.message()}"
                    is IOException -> "Error de conexión al cargar. Verifique su red."
                    else -> "Ocurrió un error inesperado al cargar los datos."
                }
                uiState = uiState.copy(initialLoadError = errorMsg, isLoading = false)
            }
        }
    }


    fun saveChanges() {
        if (adminId == 0 || uiState.display.isBlank() || uiState.username.isBlank() || uiState.email.isBlank() || uiState.securityPin.isBlank()) {
            uiState = uiState.copy(saveError = "Datos incompletos o sesión inválida.")
            return
        }

        uiState = uiState.copy(isSaving = true, saveSuccess = false, saveError = null)

        viewModelScope.launch {
            try {
                val requestDto = UserAdminDto(
                    id = adminId,
                    display = uiState.display,
                    username = uiState.username,
                    email = uiState.email,
                    adminAccess = true,
                    securityPin = uiState.securityPin,
                    capital = uiState.capital
                )

                val response = userAdminService.updateUserAdmin(adminId, requestDto)

                if (response.isSuccessful) {
                    uiState = uiState.copy(isSaving = false, saveSuccess = true, saveError = null)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido al guardar."
                    uiState = uiState.copy(isSaving = false, saveSuccess = false, saveError = "Error ${response.code()}: $errorMsg")
                }

                if (uiState.saveSuccess) {
                    delay(3000)
                    uiState = uiState.copy(saveSuccess = false)
                }

            } catch (e: Exception) {
                val errorMsg = when (e) {
                    is HttpException -> "Error de servidor al guardar: ${e.message()}"
                    is IOException -> "Error de conexión al guardar. Verifique su red."
                    else -> "Ocurrió un error inesperado al guardar los datos."
                }
                uiState = uiState.copy(isSaving = false, saveSuccess = false, saveError = errorMsg)
            }
        }
    }
}
