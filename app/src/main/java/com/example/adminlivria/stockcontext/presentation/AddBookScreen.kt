package com.example.adminlivria.stockcontext.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.adminlivria.common.inventoryServiceInstance
import com.example.adminlivria.common.ui.theme.AlexandriaFontFamily
import com.example.adminlivria.common.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaWhite
import com.example.adminlivria.common.ui.theme.LivriaYellowLight

/**
 * Componente principal para agregar un nuevo libro al inventario.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen() {
    val context = LocalContext.current

    val viewModel: AddBookViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AddBookViewModel(inventoryServiceInstance,
                    context = context.applicationContext
                ) as T
            }
        }
    )
    val uiState = viewModel.uiState

    // Mensajería de Toast
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            viewModel.clearMessages()
        }
    }
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            viewModel.clearMessages()
        }
    }


        Column(
            modifier = Modifier
                .fillMaxSize()
                // 2. CAMBIAMOS EL BACKGROUND PRINCIPAL A BLANCO
                .background(LivriaWhite)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                // 3. CAMBIAMOS EL COLOR DE LA CARD A AMARILLO (50% OPACIDAD)
                colors = CardDefaults.cardColors(containerColor = LivriaYellowLight.copy(alpha = 0.35f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        // 4. TRADUCIMOS EL TÍTULO
                        text = "ADD TO INVENTORY",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = AsapCondensedFontFamily,
                            color = LivriaOrange,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            letterSpacing = 1.sp
                        ),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                    )

                    // 1. INPUTS DE TEXTO - TRADUCIDOS
                    InputTextField(
                        label = "Title", // TRADUCIDO
                        value = uiState.title,
                        onValueChange = viewModel::onTitleChange
                    )
                    InputTextField(
                        label = "Author", // TRADUCIDO
                        value = uiState.author,
                        onValueChange = viewModel::onAuthorChange
                    )
                    InputTextField(
                        label = "Description", // TRADUCIDO
                        value = uiState.description,
                        onValueChange = viewModel::onDescriptionChange,
                        singleLine = false,
                        maxLines = 4
                    )
                    InputTextField(
                        label = "Stock", // TRADUCIDO
                        value = uiState.stock,
                        onValueChange = viewModel::onStockChange,
                        keyboardType = KeyboardType.Number
                    )

                    // 2. SELECTOR DE GÉNERO - TRADUCIDO
                    OptionSelector(
                        label = "Genre", // TRADUCIDO
                        options = BookOptions.GENRE_OPTIONS,
                        selectedOption = uiState.genre,
                        onSelect = viewModel::onGenreSelected
                    )

                    // 3. SELECTOR DE IDIOMA - TRADUCIDO
                    OptionSelector(
                        label = "Language", // TRADUCIDO
                        options = BookOptions.LANGUAGE_OPTIONS,
                        selectedOption = uiState.language,
                        onSelect = viewModel::onLanguageSelected
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4. SELECTOR DE IMAGEN DE PORTADA (Mejorado)
                    CoverImagePicker(
                        currentUri = uiState.cover,
                        onUriSelected = viewModel::onCoverUriSelected
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 5. BOTÓN DE ENVÍO - TRADUCIDO Y CON NUEVOS COLORES
                    Button(
                        onClick = viewModel::submitBook,
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            // Fondo LivriaYellowLight
                            containerColor = LivriaYellowLight,
                            // Fuente LivriaBlue (asumiendo que es tu color "Navy Blue" más oscuro)
                            contentColor = LivriaBlue
                        )
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                color = LivriaBlue, // Usamos LivriaBlue para el spinner
                                modifier = Modifier.size(28.dp)
                            )
                        } else {
                            // TRADUCIDO
                            Text(
                                "ADD BOOK",
                                fontFamily = AlexandriaFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }

                    // Mensajes de error/éxito
                    if (uiState.errorMessage != null) {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    if (uiState.successMessage != null) {
                        Text(
                            text = uiState.successMessage,
                            color = LivriaBlue, // Un color de éxito
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }


// ---------------- Componentes Reutilizables ----------------

@Composable
private fun InputTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 14.sp,
            color = Color.Black
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LivriaWhite,
            unfocusedContainerColor = LivriaWhite,
            disabledContainerColor = LivriaWhite,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedLabelColor = LivriaBlue,
            unfocusedLabelColor = LivriaBlue,
            cursorColor = LivriaBlue
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
private fun OptionSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val displayText = if (selectedOption.isBlank()) "Seleccionar $label" else selectedOption

    OutlinedTextField(
        value = displayText,
        onValueChange = { /* Solo lectura */ },
        label = {
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            )
        },
        readOnly = true,
        trailingIcon = {
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown",
                Modifier.clickable { expanded = true },
                tint = LivriaBlue
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 14.sp,
            color = Color.Black
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LivriaWhite,
            unfocusedContainerColor = LivriaWhite,
            disabledContainerColor = LivriaWhite,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedLabelColor = LivriaBlue,
            unfocusedLabelColor = LivriaBlue,
            cursorColor = LivriaBlue
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = true }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        options.forEach { selection ->
            DropdownMenuItem(
                text = { Text(selection, color = LivriaBlue) },
                onClick = {
                    onSelect(selection)
                    expanded = false
                }
            )
        }
    }
}

@Composable
private fun CoverImagePicker(
    currentUri: String,
    onUriSelected: (String) -> Unit
) {
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                onUriSelected(uri.toString())
            }
        }
    )
    // Para la cámara, necesitarías un ActivityResultContract que dispare la cámara
    // y maneje la URI de salida. Esto es un poco más complejo y podría requerir permisos.
    // Por simplicidad, aquí solo mostramos el icono y sugerimos la implementación.
    // val cameraLauncher = rememberLauncherForActivityResult(
    //     contract = ActivityResultContracts.TakePicture(),
    //     onResult = { success: Boolean ->
    //         if (success) { /* Manejar la URI de la foto tomada */ }
    //     }
    // )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Book Cover",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = AlexandriaFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = LivriaBlue
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Contenedor visual para la imagen con acciones de carga
        Box(
            modifier = Modifier
                .size(180.dp, 240.dp) // Tamaño ligeramente más grande para la imagen
                .clip(RoundedCornerShape(12.dp))
                .background(LivriaLightGray.copy(alpha = 0.3f))
                .border(2.dp, LivriaSoftCyan, RoundedCornerShape(12.dp)) // Borde claro
                .clickable { galleryLauncher.launch("image/*") }, // Área clicable
            contentAlignment = Alignment.Center
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context)
                    .data(data = if (currentUri.isNotBlank()) currentUri else "https://via.placeholder.com/150x200/F0F0F0/888888?text=Seleccionar+Portada") // Placeholder estilizado
                    .crossfade(true)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "Portada del libro seleccionada",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay para mostrar el icono de carga si no hay imagen
            if (currentUri.isBlank()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.Image,
                        contentDescription = "Seleccionar Imagen",
                        modifier = Modifier.size(60.dp),
                        tint = LivriaBlue.copy(alpha = 0.6f)
                    )
                    Text(
                        "Tap to select",
                        color = LivriaBlue.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botones de acción explícitos para Cámara y Galería
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón para Galería
            OutlinedButton(
                onClick = { galleryLauncher.launch("image/*") },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                    contentColor = LivriaBlue
                ),
                border = BorderStroke(1.dp, LivriaBlue),
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            ) {
                Icon(Icons.Filled.Image, contentDescription = "Galería", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Gallery")
            }

            // Botón para Cámara (requiere implementación de permisos y TakePicture)
            OutlinedButton(
                onClick = {
                    // Implementar lanzamiento de la cámara aquí
                    Toast.makeText(context, "Funcionalidad de Cámara (en desarrollo)", Toast.LENGTH_SHORT).show()
                    // cameraLauncher.launch(obtenerUriParaGuardarFoto())
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = LivriaSoftCyan.copy(alpha = 0.5f),
                    contentColor = LivriaBlue
                ),
                border = BorderStroke(1.dp, LivriaBlue),
                modifier = Modifier.weight(1f).padding(start = 4.dp)
            ) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Cámara", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Camera")
            }
        }
        if (currentUri.isNotBlank()) {
            Text(
                text = "Image selected",
                style = MaterialTheme.typography.bodySmall,
                color = LivriaBlue,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}