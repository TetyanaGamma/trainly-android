package com.example.trainly.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.net.toUri

fun launchCall(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Не удалось открыть звонилку", Toast.LENGTH_SHORT).show()
    }
}

fun launchWhatsApp(context: Context, phoneNumber: String) {
    try {
        // Очищаем номер: оставляем только цифры
        val cleanNumber = phoneNumber.filter { it.isDigit() }
        // WhatsApp требует формат без + в начале ссылки wa.me
        val url = "https://wa.me/$cleanNumber"

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = url.toUri()
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp не установлен", Toast.LENGTH_SHORT).show()
    }
}

fun launchSms(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "smsto:$phoneNumber".toUri()
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Не удалось открыть SMS", Toast.LENGTH_SHORT).show()
    }
}