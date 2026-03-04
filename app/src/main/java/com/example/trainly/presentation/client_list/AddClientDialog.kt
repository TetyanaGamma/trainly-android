package com.example.trainly.presentation.client_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.trainly.R
import com.example.trainly.ui.theme.AppDimensions

@Composable
fun AddClientDialog(
    initialName: String = "",       // По умолчанию пусто (для создания)
    initialPhone: String = "",
    initialWorkouts: Int = 0,
    onDismiss: () -> Unit,
    onConfirm: (name: String,
            phone: String,
            workouts: Int) -> Unit
) {

    // 1. Определяем страну (например, "GE" или "UA")
    val countryCode = remember { java.util.Locale.getDefault().country.uppercase() }

    // 2. Создаем подсказку
    val dynamicPlaceholder = remember(countryCode) {
        when (countryCode) {
            "GE" -> "+995..."
            "UA" -> "+380..."
            "PL" -> "+48..."
            "KZ" -> "+7..."
            "IL" -> "+972..."
            else -> "+..."
        }
    }

    // Состояния полей ввода внутри диалога
    var name by remember { mutableStateOf(initialName) }
    var phone by remember { mutableStateOf(initialPhone) }
    var workouts by remember { mutableStateOf(initialWorkouts.toString()) }

    // Валидация имени: минимум 3 символа
    val isNameValid by remember {
        derivedStateOf { name.trim().length >= 3 }
    }

    // Кнопка активна, если заполнено хотя бы имя
    val canSave by remember { derivedStateOf { isNameValid } }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(AppDimensions.cardCornerRadius),
        title = {
            Text(
                text = stringResource(R.string.add_client_dialog_title),
                style = MaterialTheme.typography.displayMedium // Inter Thin 24sp
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingMedium),
                modifier = Modifier.padding(top = AppDimensions.paddingSmall)
            ) {
                // Поле ввода имени обязательное
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.hint_client_name)) },
                    placeholder = { Text(stringResource(R.string.input_client_name)) },
                    isError = name.isNotEmpty() && !isNameValid,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimensions.cardCornerRadius),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                // ПОЛЕ ТЕЛЕФОНА (Международный формат)
                OutlinedTextField(
                    value = phone,
                    onValueChange = { input ->
                        // Разрешаем вводить цифры, плюс, скобки и дефисы для удобства
                        if (input.all { it.isDigit() || it == '+' || it == '-' || it == '(' || it == ')' || it == ' ' }) {
                            phone = input
                        }
                    },
                    label = { Text(stringResource(R.string.client_phone_label)) },
                    placeholder = { Text(dynamicPlaceholder) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Поле ввода тренировок
                OutlinedTextField(
                    value = workouts,
                    onValueChange = { input ->
                        if (input.isEmpty() || input.all { it.isDigit() }) {
                            workouts = input
                        }
                    },
                    label = { Text(stringResource(R.string.hint_total_workouts)) },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppDimensions.cardCornerRadius),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(name.trim(), phone.trim(), workouts.toIntOrNull() ?: 0)
                },
                // Кнопка будет неактивна, если имя < 3 символов
                enabled = canSave,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(AppDimensions.cardCornerRadius)
            ) {
                Text(stringResource(R.string.btn_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.btn_cancel),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}