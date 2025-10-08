package com.example.adminlivria.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminlivria.data.remote.AuthService
import com.example.adminlivria.common.authServiceInstance
import com.example.adminlivria.presentation.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.presentation.ui.theme.LivriaAmber
import com.example.adminlivria.presentation.ui.theme.LivriaOrange


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    authService: AuthService = authServiceInstance
) {
    val factory = remember { LoginViewModelFactory(authService) }

    val viewModel: LoginViewModel = viewModel(factory = factory)

    val uiState = viewModel.uiState

    LaunchedEffect(key1 = uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            onLoginSuccess()
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card {
                Text(
                    text = "ADMIN SIGN IN",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AsapCondensedFontFamily,
                        color = LivriaOrange,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier.padding(bottom = 40.dp)
                )

                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = viewModel::onUsernameChange,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.securityPin,
                    onValueChange = viewModel::onSecurityPinChange,
                    label = { Text("Security Pin") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = viewModel::signInAdmin,
                    enabled = !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                    } else {
                        Text("SIGN IN")
                    }
                }

                uiState.error?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}