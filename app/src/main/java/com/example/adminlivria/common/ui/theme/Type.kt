package com.example.adminlivria.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.adminlivria.R

// --- DEFINICIÓN DE LAS FAMILIAS DE FUENTES ---

// 1. FUENTE ALEXANDRIA (Texto Regular)
val AlexandriaFontFamily = FontFamily(
    // Alexandria Light
    Font(R.font.alexandria_light, FontWeight.Light),     // Peso 300
    // Alexandria Regular
    Font(R.font.alexandria_regular, FontWeight.Normal),  // Peso 400
    // Alexandria Medium
    Font(R.font.alexandria_medium, FontWeight.Medium),   // Peso 500
    // Alexandria SemiBold
    Font(R.font.alexandria_semibold, FontWeight.SemiBold),// Peso 600
    // Alexandria Bold
    Font(R.font.alexandria_bold, FontWeight.Bold)        // Peso 700
)

// 2. FUENTE ASAP CONDENSED (Logo y Títulos)
val AsapCondensedFontFamily = FontFamily(
    // Asap Condensed Regular
    Font(R.font.asap_condensed_regular, FontWeight.Normal),
    // Asap Condensed Medium
    Font(R.font.asap_condensed_medium, FontWeight.Medium),
    // Asap Condensed SemiBold (asumo que se mapea a SemiBold)
    Font(R.font.asap_condensed_semibold, FontWeight.SemiBold),
    // Asap Condensed Bold
    Font(R.font.asap_condensed_bold, FontWeight.Bold),
    // Asap Condensed Black
    Font(R.font.asap_condensed_black, FontWeight.Black)  // Peso 900
)



val Typography = Typography(

    // LOGO Y TÍTULOS (usando Asap Condensed)

    // Títulos grandes y prominentes (H1, H2, etc.)
    displayLarge = TextStyle(
        fontFamily = AsapCondensedFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 57.sp
    ),

    // Títulos de secciones o encabezados principales (H3, H4)
    titleLarge = TextStyle(
        fontFamily = AsapCondensedFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),

    // TEXTO REGULAR (usando Alexandria)

    // Texto de cuerpo principal para párrafos
    bodyLarge = TextStyle(
        fontFamily = AlexandriaFontFamily,
        fontWeight = FontWeight.Normal, // Peso Regular (400)
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // Texto secundario o leyendas (Medium, Light)
    bodyMedium = TextStyle(
        fontFamily = AlexandriaFontFamily,
        fontWeight = FontWeight.Medium, // Peso Medium (500)
        fontSize = 14.sp
    ),

    // Texto de botones o etiquetas pequeñas
    labelSmall = TextStyle(
        fontFamily = AlexandriaFontFamily,
        fontWeight = FontWeight.Light, // Peso Light (300)
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
    )
)