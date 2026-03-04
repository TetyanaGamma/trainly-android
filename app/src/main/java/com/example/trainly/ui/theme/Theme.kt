package com.example.trainly.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.trainly.R

@Composable
fun TrainlyTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
        primary = colorResource(id = R.color.brand_gold),
        onPrimary = colorResource(id = R.color.brand_black),
        background = colorResource(id = R.color.brand_white),
        onBackground = colorResource(id = R.color.brand_black),
        surface = colorResource(id = R.color.brand_white),
        onSurface = colorResource(id = R.color.brand_black),
        outline = colorResource(id = R.color.brand_border),
        error = colorResource(id = R.color.brand_error)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Настройки шрифтов всё же лучше держать в коде (Type.kt)
        content = content
    )
}