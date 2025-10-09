package com.example.adminlivria.profilecontext.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminlivria.R
import com.example.adminlivria.profilecontext.data.remote.AuthService
import com.example.adminlivria.common.authServiceInstance
import com.example.adminlivria.common.ui.theme.AlexandriaFontFamily
import com.example.adminlivria.common.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.common.ui.theme.LivriaBlack
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaWhite
import com.example.adminlivria.common.ui.theme.LivriaYellowLight


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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Livria Logo",
                modifier = Modifier.height(50.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = LivriaYellowLight.copy(alpha = 0.35f),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "ADMIN SIGN IN",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AsapCondensedFontFamily,
                        color = LivriaOrange,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 50.dp, bottom = 15.dp)
                )

                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = viewModel::onUsernameChange,
                    label = {
                        Text(
                            "Username",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = LivriaBlack
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = LivriaWhite,
                        unfocusedContainerColor = LivriaWhite,
                        disabledContainerColor = LivriaWhite,
                        focusedTextColor = LivriaBlack,
                        unfocusedTextColor = LivriaBlack,
                        focusedIndicatorColor = LivriaBlue,
                        unfocusedIndicatorColor = LivriaLightGray,
                        focusedLabelColor = LivriaBlue,
                        unfocusedLabelColor = LivriaBlue,
                        cursorColor = LivriaBlue
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                )

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = {
                        Text(
                            "Password",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = LivriaBlack
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = LivriaWhite,
                        unfocusedContainerColor = LivriaWhite,
                        disabledContainerColor = LivriaWhite,
                        focusedTextColor = LivriaBlack,
                        unfocusedTextColor = LivriaBlack,
                        focusedIndicatorColor = LivriaBlue,
                        unfocusedIndicatorColor = LivriaLightGray,
                        focusedLabelColor = LivriaBlue,
                        unfocusedLabelColor = LivriaBlue,
                        cursorColor = LivriaBlue
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                )

                OutlinedTextField(
                    value = uiState.securityPin,
                    onValueChange = viewModel::onSecurityPinChange,
                    label = {
                        Text(
                            "Security Pin",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = LivriaBlack
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = LivriaWhite,
                        unfocusedContainerColor = LivriaWhite,
                        disabledContainerColor = LivriaWhite,
                        focusedTextColor = LivriaBlack,
                        unfocusedTextColor = LivriaBlack,
                        focusedIndicatorColor = LivriaBlue,
                        unfocusedIndicatorColor = LivriaLightGray,
                        focusedLabelColor = LivriaBlue,
                        unfocusedLabelColor = LivriaBlue,
                        cursorColor = LivriaBlue
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                )

                uiState.error?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = viewModel::signInAdmin,
                    enabled = !uiState.isLoading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = LivriaOrange,
                        contentColor = LivriaWhite
                    ),
                    modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 30.dp)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                    } else {
                        Text("SIGN IN", fontFamily = AlexandriaFontFamily, fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))

            }
        }
    }
}