package com.example.trainly.presentation.client_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trainly.R
import com.example.trainly.domain.models.Client
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.trainly.presentation.navigation.Screen
import com.example.trainly.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientListScreen(
    navController: NavController,
    viewModel: ClientListViewModel = koinViewModel()
) {
    val clients by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        // Используем цвет фона из TrainlyTheme (белый)
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.clients_title),
                        style = MaterialTheme.typography.displayLarge // Inter Thin 32sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                // Отступ 32dp для "воздуха"
                modifier = Modifier.padding(top = AppDimensions.screenTopPadding)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true},
                containerColor = MaterialTheme.colorScheme.primary, // Золотой
                contentColor = MaterialTheme.colorScheme.onPrimary, // Почти черный текст/иконка
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(AppDimensions.cardElevation)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_client_content_desc)
                )
            }
        }
    ) { padding ->
        if (clients.isEmpty()) {
            EmptyState(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = AppDimensions.paddingMedium, vertical = AppDimensions.paddingLarge),
                verticalArrangement = Arrangement.spacedBy(AppDimensions.itemSpacing)
            ) {
                items(clients) { client ->
                    ClientCard(client = client,
                        onClick = {
                          navController.navigate(Screen.ClientProfile.createRoute(client.id))
                        }
                    )
                }
            }
        }
    }
    if (showDialog) {
        AddClientDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name,phone, count ->
                viewModel.addClient(name, phone, count)
                showDialog = false
            }
        )
    }
}

@Composable
fun ClientCard(client: Client,
               onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {onClick()},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(AppDimensions.cardCornerRadius),
        // Тонкая линия из ресурсов (#E7E7E7)
        border = BorderStroke(AppDimensions.borderStrokeThin, MaterialTheme.colorScheme.outline)

    ) {
        Row(
            modifier = Modifier
                .padding(AppDimensions.paddingMedium)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Фото-заглушка
            Box(
                modifier = Modifier
                    .size(AppDimensions.photoSize)
                    .background(MaterialTheme.colorScheme.outlineVariant, CircleShape)
            )

            Spacer(modifier = Modifier.width(AppDimensions.paddingMedium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = client.name,
                    style = MaterialTheme.typography.bodyLarge, // Inter Regular 16sp
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    // Используем строковый ресурс workouts_status: "%1$d/%2$d"
                    text = stringResource(R.string.workouts_status, client.workoutsUsed, client.totalWorkoutsPaid),
                    style = MaterialTheme.typography.bodySmall, // Inter Thin 14sp
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Делаем мягким, но читаемым
                )
            }

            // Остаток тренировок
            Text(
                text = "${client.workoutsLeft}",
                style = MaterialTheme.typography.bodyLarge,
                // Красный при ошибке/нужде в оплате, иначе золотой
                color = if (client.needsPayment) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun EmptyState(modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.no_clients_yet),
                style = MaterialTheme.typography.bodyLarge, // Увеличили размер до 16sp
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f) // Сделали контрастнее
            )
        }

    }
}