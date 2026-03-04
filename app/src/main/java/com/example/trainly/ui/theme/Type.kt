package com.example.trainly.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.trainly.R

// Регистрируем семейство шрифтов
val Inter = FontFamily(
    Font(R.font.inter_thin, FontWeight.Thin),
    Font(R.font.inter_regular, FontWeight.Normal)
)

val Typography = Typography(
    // headder 1 (28–32pt, Thin) — Main headders
    displayLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Thin,
        fontSize = 32.sp,
        letterSpacing = (-0.5).sp
    ),

    // Headder 2 (22–24pt, Thin) — Subheadders
    displayMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Thin,
        fontSize = 24.sp
    ),

    // Text / Text input (16pt, Regular)
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    // Titles  (13–14pt, Thin)
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp
    ),

    // Тables and graphs (12–14pt, Regular)
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    // Buttons (16pt, Medium/Regular)
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)
