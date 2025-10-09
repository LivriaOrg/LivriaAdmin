package com.example.adminlivria.profilecontext.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.profilecontext.data.model.LoginAdminRequest
import com.example.adminlivria.profilecontext.data.remote.AuthService
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val securityPin: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false
)

class LoginViewModel(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(newUsername: String) {
        uiState = uiState.copy(username = newUsername, error = null)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, error = null)
    }

    fun onSecurityPinChange(newPin: String) {
        uiState = uiState.copy(securityPin = newPin, error = null)
    }

    fun signInAdmin() {
        if (uiState.username.isBlank() || uiState.password.isBlank() || uiState.securityPin.isBlank()) {
            uiState = uiState.copy(error = "Por favor, complete todos los campos.", isAuthenticated = false)
            return
        }

        uiState = uiState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val request = LoginAdminRequest(
                    username = uiState.username,
                    password = uiState.password,
                    securityPin = uiState.securityPin
                )

                val response = authService.signInAdmin(request)

                if (response.isSuccessful) {
                    val authResponse = response.body()

                    if (authResponse?.success == true && authResponse.token != null) {

                        val adminId = authResponse.id ?: 0
                        tokenManager.saveAuthData(authResponse.token, adminId)

                        uiState = uiState.copy(
                            isAuthenticated = true,
                            isLoading = false
                        )

                    } else {

                        uiState = uiState.copy(
                            error = authResponse?.message ?: "Error desconocido. Intente nuevamente.",
                            isLoading = false,
                            isAuthenticated = false
                        )
                    }
                } else {
                    uiState = uiState.copy(
                        error = "Credenciales incorrectas o PIN inválido. (Error ${response.code()})",
                        isLoading = false,
                        isAuthenticated = false
                    )
                }
            } catch (e: HttpException) {
                uiState = uiState.copy(
                    error = "Error de servidor: ${e.message()}",
                    isLoading = false,
                    isAuthenticated = false
                )
            } catch (e: IOException) {
                uiState = uiState.copy(
                    error = "Error de conexión: No se pudo conectar al servidor. Verifique su red.",
                    isLoading = false,
                    isAuthenticated = false
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = "Ocurrió un error inesperado.",
                    isLoading = false,
                    isAuthenticated = false
                )
            }
        }
    }
}