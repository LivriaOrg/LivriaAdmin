package com.example.adminlivria.profilecontext.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import com.example.adminlivria.common.ui.theme.AlexandriaFontFamily
import com.example.adminlivria.common.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.common.ui.theme.LivriaAmber
import com.example.adminlivria.common.ui.theme.LivriaBlack
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaNavyBlue
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaWhite
import com.example.adminlivria.common.ui.theme.LivriaYellowLight

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    val state = viewModel.uiState

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(paddingValues)
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 0.dp),
                    colors = CardDefaults.cardColors(containerColor = LivriaWhite),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        SettingsHeader()
                        Spacer(modifier = Modifier.height(18.dp))
                        WelcomeCard(state.user.username, state.user.fullName)
                        Spacer(modifier = Modifier.height(18.dp))
                        TabSelector(
                            isProfileSelected = state.isProfileTabSelected,
                            onSelectProfile = { viewModel.setTab(true) },
                            onSelectApplication = { viewModel.setTab(false) }
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        if (state.isProfileTabSelected) {
                            ProfileSettingsContent(state, viewModel)
                        } else {
                            ApplicationSettingsContent(viewModel)
                        }
                    }
                }
            }
        }
    }
}

// ---------------- Componentes ----------------

@Composable
fun SettingsHeader() {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Text(
            "Settings",
            textAlign = TextAlign.Center,
            color = LivriaOrange,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(6.dp)
        )
        Text(
            "Manage your account and system preferences",
            textAlign = TextAlign.Center,
            color = LivriaBlack,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 3.dp)
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 18.dp),
            thickness = 2.dp,
            color = LivriaSoftCyan
        )
    }
}

@Composable
fun WelcomeCard(username: String, role: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LivriaYellowLight.copy(alpha = 0.35f))
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(LivriaOrange),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (username.isNotEmpty()) username.first().uppercase() else "?",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = AlexandriaFontFamily
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Hello, $username!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = LivriaBlue
                )
            )
            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = LivriaBlue
                )
            )
        }
    }
}

@Composable
fun TabSelector(
    isProfileSelected: Boolean,
    onSelectProfile: () -> Unit,
    onSelectApplication: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val customChipColors = FilterChipDefaults.filterChipColors(
            labelColor = LivriaBlue,
            selectedLabelColor = LivriaBlue,
            containerColor = Color.Transparent,
            selectedContainerColor = LivriaBlue.copy(alpha = 0.35f)
        )
        FilterChip(
            selected = isProfileSelected,
            onClick = onSelectProfile,
            colors = customChipColors,
            shape = RoundedCornerShape(10.dp),
            border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isProfileSelected,
                borderColor = Color.Transparent,
                selectedBorderColor = Color.Transparent,
                borderWidth = 0.dp
            ),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp).height(40.dp),
            label = {
                Text(
                    "PROFILE",
                    fontFamily = AlexandriaFontFamily,
                    fontSize = 14.sp,
                    color = LivriaBlue,
                    fontWeight = FontWeight.SemiBold
                ) }        )
        FilterChip(
            selected = !isProfileSelected,
            onClick = onSelectApplication,
            colors = customChipColors,
            shape = RoundedCornerShape(10.dp),
            border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = !isProfileSelected,
                borderColor = Color.Transparent,
                selectedBorderColor = Color.Transparent,
                borderWidth = 0.dp
            ),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp).height(40.dp),
            label = {
                Text(
                    "APPLICATION",
                    fontFamily = AlexandriaFontFamily,
                    fontSize = 14.sp,
                    color = LivriaBlue,
                    fontWeight = FontWeight.SemiBold
                ) }
        )
    }
}

// ---------- Profile Content ----------

@Composable
fun ProfileSettingsContent(state: SettingsUiState, viewModel: SettingsViewModel) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "PROFILE INFORMATION",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = AsapCondensedFontFamily,
                color = LivriaAmber,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                letterSpacing = 2.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = state.user.fullName,
            onValueChange = { viewModel.updateField("fullName", it) },
            label = {
                Text(
                    "Name",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                unfocusedContainerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.user.email,
            onValueChange = { viewModel.updateField("email", it) },
            label = {
                Text(
                    "Email",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                unfocusedContainerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.securityPin,
            onValueChange = { viewModel.updateField("securityPin", it) },
            label = {
                Text(
                    "Security Pin",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                unfocusedContainerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        if (state.saveSuccess) {
            Text(
                "Changes saved successfully!",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp),
                fontFamily = AlexandriaFontFamily
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = viewModel::logout,
                enabled = !state.isSaving,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = LivriaOrange,
                    contentColor = LivriaWhite
                ),
                modifier = Modifier.weight(0.4f).height(50.dp).padding(end = 8.dp)
            ) {
                Text("LOG OUT", fontFamily = AlexandriaFontFamily, )
            }

            Button(
                onClick = viewModel::saveChanges,
                enabled = !state.isSaving,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LivriaSoftCyan,
                    contentColor = LivriaBlue
                ),
                modifier = Modifier.weight(0.6f).height(50.dp)
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = LivriaBlue)
                } else {
                    Text("SAVE CHANGES", fontFamily = AlexandriaFontFamily)
                }
            }
        }
    }
}

// ---------- Application Content ----------

@Composable
fun ApplicationSettingsContent(viewModel: SettingsViewModel) {
    val state = viewModel.uiState

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "APP SETTINGS",
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = AsapCondensedFontFamily,
                color = LivriaAmber,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        // 1. Notificaciones
        SettingRow(
            title = "Notifications",
            description = "Receive notifications in the app",
            isEnabled = state.receiveNotifications,
            onToggle = { viewModel.updateApplicationSetting("notifications", it) }
        )

        // 2. Email Alerts
        SettingRow(
            title = "Email Alerts",
            description = "Receive email alerts",
            isEnabled = state.receiveEmailAlerts,
            onToggle = { viewModel.updateApplicationSetting("emailAlerts", it) }
        )

        // 3. Auto Save
        SettingRow(
            title = "Auto Save",
            description = "Automatically save changes",
            isEnabled = state.autoSaveEnabled,
            onToggle = { viewModel.updateApplicationSetting("autoSave", it) }
        )

        // --- SECCIÓN DE BOTONES (Duplicado de ProfileSettingsContent) ---

        if (state.saveSuccess) {
            Text(
                "Changes saved successfully!",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp),
                fontFamily = AlexandriaFontFamily,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp)) // Espacio extra para separarlo de los toggles

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = viewModel::logout,
                enabled = !state.isSaving,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = LivriaOrange,
                    contentColor = LivriaWhite
                ),
                modifier = Modifier.weight(0.4f).height(50.dp).padding(end = 8.dp)
            ) {
                Text("Log Out", fontFamily = AlexandriaFontFamily)
            }

            Button(
                onClick = viewModel::saveChanges,
                enabled = !state.isSaving,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LivriaSoftCyan,
                    contentColor = LivriaBlue
                ),
                modifier = Modifier.weight(0.6f).height(50.dp)
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = LivriaBlue)
                } else {
                    Text("Save Changes", fontFamily = AlexandriaFontFamily)
                }
            }
        }
        // --- FIN SECCIÓN DE BOTONES ---
    }
}

// Componente reusable para cada fila de configuración (el YES/NO)
@Composable
fun SettingRow(
    title: String,
    description: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = AlexandriaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = LivriaNavyBlue
                )
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = AlexandriaFontFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            )
        }

        // El control YES/NO (Usamos dos Buttons/Chips para imitar tu diseño)
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(LivriaLightGray.copy(alpha = 0.7f))
                .height(32.dp),
        ) {
            SettingToggleChip(
                label = "YES",
                isSelected = isEnabled,
                onClick = { onToggle(true) }
            )
            SettingToggleChip(
                label = "NO",
                isSelected = !isEnabled,
                onClick = { onToggle(false) }
            )
        }
    }
}

@Composable
private fun SettingToggleChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) LivriaOrange else Color.Transparent
    val contentColor = if (isSelected) Color.White else LivriaNavyBlue

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .height(IntrinsicSize.Max)
            .width(50.dp)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor,
            fontFamily = AlexandriaFontFamily
        )
    }
}
