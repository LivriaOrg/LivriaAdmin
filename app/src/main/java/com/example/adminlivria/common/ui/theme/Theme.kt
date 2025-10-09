package com.example.adminlivria.common.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ----------------------------------------------------------------------
// 1. DEFINICIÓN DEL ESQUEMA DE COLOR CLARO (LIGHT)
// ----------------------------------------------------------------------
private val LightColorScheme = lightColorScheme(
    // Colores principales de la marca
    primary = LivriaOrange,        // Anaranjado (#FF5C00)
    onPrimary = Color.White,       // Texto/Ícono sobre Anaranjado
    secondary = LivriaAmber,       // Ámbar (#FEB913)
    onSecondary = Color.Black,     // Texto/Ícono sobre Ámbar
    tertiary = LivriaBlue,         // Azul (#2364A0)
    onTertiary = Color.White,

    // Fondos y Superficies
    background = LivriaWhite,  // Gris Claro para el fondo de la pantalla
    onBackground = Color.Black,    // Texto sobre fondo
    surface = LivriaLightGray,         // Fondo de componentes (Tarjetas)
    onSurface = Color.Black,       // Texto sobre superficie

    // Color de error
    error = Color(0xFFB00020),
    onError = Color.White
)

// ----------------------------------------------------------------------
// 2. DEFINICIÓN DEL ESQUEMA DE COLOR OSCURO (DARK)
// ----------------------------------------------------------------------
private val DarkColorScheme = darkColorScheme(
    // Colores principales de la marca (mantienen su valor)
    primary = LivriaOrange,        // Anaranjado
    onPrimary = Color.Black,       // Texto sobre Anaranjado (para alto contraste)
    secondary = LivriaAmber,       // Ámbar
    onSecondary = Color.Black,
    tertiary = LivriaNavyBlue,     // Azul Marino oscuro
    onTertiary = Color.White,

    // Fondos y Superficies (tonos oscuros)
    background = DarkGrayDeep,     // Fondo muy oscuro (#121212)
    onBackground = Color.White,    // Texto principal
    surface = DarkGrayMedium,      // Superficie de componentes (#1E1E1E)
    onSurface = Color.White,       // Texto sobre superficie

    // Color de error
    error = Color(0xFFCF6679),
    onError = Color.Black
)

// ----------------------------------------------------------------------
// 3. FUNCIÓN COMPOSABLE CON LOS STYLE GUIDELINES DE COLOR Y TIPOGRAFÍA
// ----------------------------------------------------------------------
@Composable
fun AdminLivriaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}