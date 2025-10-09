package com.example.adminlivria.profilecontext.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.profilecontext.data.remote.UserAdminService
import com.example.adminlivria.profilecontext.data.model.UserAdminDto
// NUEVOS IMPORTS para Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    // CAMBIO CLAVE: Usamos MutableStateFlow internamente
    private val _uiState = MutableStateFlow(SettingsUiState())
    // Y lo exponemos como StateFlow inmutable para ser consumido por Compose
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val adminId: Int = tokenManager.getAdminId()

    init {
        loadAdminData()
    }


    fun updateField(field: String, value: String) {
        _uiState.update { currentState ->
            val updatedState = when (field) {
                "fullName" -> currentState.copy(display = value)
                "username" -> currentState.copy(username = value)
                "email" -> currentState.copy(email = value)
                "securityPin" -> currentState.copy(securityPin = value)
                else -> currentState
            }
            updatedState.copy(saveSuccess = false, saveError = null)
        }
    }

    fun setTab(isProfile: Boolean) {
        _uiState.update { it.copy(isProfileTabSelected = isProfile) }
    }

    fun updateApplicationSetting(setting: String, isEnabled: Boolean) {
        _uiState.update { currentState ->
            when (setting) {
                "notifications" -> currentState.copy(receiveNotifications = isEnabled, saveSuccess = false)
                "emailAlerts" -> currentState.copy(receiveEmailAlerts = isEnabled, saveSuccess = false)
                "autoSave" -> currentState.copy(autoSaveEnabled = isEnabled, saveSuccess = false)
                else -> currentState
            }
        }
    }

    fun logout() {
        tokenManager.clearAuthData()
        println("Cierre de sesión completado.")
    }


    fun loadAdminData() {
        if (adminId == 0) {
            _uiState.update { it.copy(initialLoadError = "Error: Sesión no válida. Por favor, vuelva a iniciar sesión.", isLoading = false) }
            return
        }

        _uiState.update { it.copy(isLoading = true, initialLoadError = null) }

        viewModelScope.launch {
            try {
                val response = userAdminService.getUserAdminData()

                if (response.isSuccessful) {
                    val adminList = response.body()
                    val adminDto = adminList?.firstOrNull()
                    adminDto?.let {
                        _uiState.update { state ->
                            state.copy(
                                display = it.display,
                                username = it.username,
                                email = it.email,
                                securityPin = it.securityPin,
                                capital = adminDto.capital,
                                isLoading = false
                            )
                        }
                    }
                } else {
                    _uiState.update { it.copy(
                        initialLoadError = "No se pudieron cargar los datos. (Error ${response.code()})",
                        isLoading = false
                    ) }
                }
            } catch (e: Exception) {
                val errorMsg = when (e) {
                    is HttpException -> "Error de servidor al cargar: ${e.message()}"
                    is IOException -> "Error de conexión al cargar. Verifique su red."
                    else -> "Ocurrió un error inesperado al cargar los datos."
                }
                _uiState.update { it.copy(initialLoadError = errorMsg, isLoading = false) }
            }
        }
    }


    fun saveChanges() {
        if (adminId == 0 || uiState.value.display.isBlank() || uiState.value.username.isBlank() || uiState.value.email.isBlank() || uiState.value.securityPin.isBlank()) {
            _uiState.update { it.copy(saveError = "Datos incompletos o sesión inválida.") }
            return
        }

        _uiState.update { it.copy(isSaving = true, saveSuccess = false, saveError = null) }

        viewModelScope.launch {
            try {
                val currentState = uiState.value
                val requestDto = UserAdminDto(
                    id = adminId,
                    display = currentState.display,
                    username = currentState.username,
                    email = currentState.email,
                    adminAccess = true,
                    securityPin = currentState.securityPin,
                    capital = currentState.capital
                )

                val response = userAdminService.updateUserAdmin(adminId, requestDto)

                if (response.isSuccessful) {
                    _uiState.update { it.copy(isSaving = false, saveSuccess = true, saveError = null) }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Error desconocido al guardar."
                    _uiState.update { it.copy(isSaving = false, saveSuccess = false, saveError = "Error ${response.code()}: $errorMsg") }
                }

                if (uiState.value.saveSuccess) {
                    delay(3000)
                    _uiState.update { it.copy(saveSuccess = false) }
                }

            } catch (e: Exception) {
                val errorMsg = when (e) {
                    is HttpException -> "Error de servidor al guardar: ${e.message()}"
                    is IOException -> "Error de conexión al guardar. Verifique su red."
                    else -> "Ocurrió un error inesperado al guardar los datos."
                }
                _uiState.update { it.copy(isSaving = false, saveSuccess = false, saveError = errorMsg) }
            }
        }
    }
}
