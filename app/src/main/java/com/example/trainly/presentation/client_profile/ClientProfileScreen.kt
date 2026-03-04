package com.example.trainly.presentation.client_profile

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trainly.R
import com.example.trainly.presentation.client_list.AddClientDialog
import com.example.trainly.presentation.navigation.Screen
import com.example.trainly.ui.theme.AppDimensions
import com.example.trainly.util.launchCall
import com.example.trainly.util.launchSms
import com.example.trainly.util.launchWhatsApp
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class MenuItem(
    val titleRes: Int,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientProfileScreen(
    navController: NavController,
    clientId: String,
    viewModel: ClientProfileViewModel = koinViewModel { parametersOf(clientId) }
) {
    // Получаем состояние клиента
    val client by viewModel.client.collectAsState()

    var showEditDialog by remember { mutableStateOf(false) }

    val menuItems = remember {
        listOf(
            MenuItem(R.string.client_routine, Icons.Outlined.FitnessCenter, "program"),
            MenuItem(R.string.progress_charts, Icons.Outlined.ShowChart, "charts"),
            MenuItem(R.string.workout_journal, Icons.Outlined.HistoryEdu, "journal"),
            MenuItem(R.string.client_form, Icons.Outlined.PersonSearch, "form"),
            MenuItem(R.string.client_payments, Icons.Outlined.Payments, "payments"),
            MenuItem(R.string.client_photo, Icons.Outlined.PhotoLibrary, "photos")
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.profile_back_content_desc)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        // ВСЯ ЛОГИКА ВНУТРИ ПАДДИНГОВ SCAFFOLD
        client?.let { currentClient ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = AppDimensions.paddingMedium)
            ) {
                ClientHeaderSection(
                    name = currentClient.name,
                    phone = currentClient.phone,
                    workoutsLeft = currentClient.workoutsLeft
                )

                Spacer(modifier = Modifier.height(AppDimensions.paddingLarge))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.itemSpacing),
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.itemSpacing),
                    modifier = Modifier.weight(1f)
                ) {
                    items(menuItems) { item ->
                        MenuCard(item) {
                            val route = when(item.titleRes) {
                                R.string.client_routine -> Screen.ClientRoutine.createRoute(clientId)
                                R.string.progress_charts -> Screen.ClientCharts.createRoute(clientId)
                                R.string.workout_journal -> Screen.ClientWorkouts.createRoute(clientId)
                                R.string.client_form -> Screen.ClientForm.createRoute(clientId)
                               R.string.client_photo -> Screen.ClientPhoto.createRoute(clientId)
                                R.string.client_payments -> Screen.ClientPayments.createRoute(clientId)
                                else -> ""
                            }
                            if (route.isNotEmpty()) {
                                navController.navigate(route)
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = AppDimensions.paddingMedium),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
                ) {
                    OutlinedButton(
                        onClick = { showEditDialog = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(AppDimensions.cardCornerRadius),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Text(stringResource(R.string.btn_edit), style = MaterialTheme.typography.labelLarge)
                    }
                    if (showEditDialog) {
                        client?.let { currentClient ->
                            AddClientDialog(
                                initialName = currentClient.name,
                                initialPhone = currentClient.phone,
                                initialWorkouts = currentClient.totalWorkoutsPaid, // Используем общее кол-во
                                onDismiss = { showEditDialog = false },
                                onConfirm = { newName, newPhone, newWorkouts ->
                                    // Вызываем метод обновления во ViewModel
                                    viewModel.updateClient(newName, newPhone, newWorkouts)
                                    showEditDialog = false
                                }
                            )
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.deleteClient()
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(AppDimensions.cardCornerRadius),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(stringResource(R.string.btn_delete), color = Color.White)
                    }
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun ClientHeaderSection(name: String,
                         phone: String,
                        workoutsLeft: Int) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(AppDimensions.profileAvatarSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
        Spacer(modifier = Modifier.height(AppDimensions.paddingMedium))
        Text(text = name, style = MaterialTheme.typography.displayMedium)
        Text(
            text = stringResource(R.string.workouts_left_label, workoutsLeft),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(AppDimensions.paddingMedium))

        if (phone.isNotBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingSmall)
            ) {
                // Кнопка ПОЗВОНИТЬ
                IconButton(onClick = { launchCall(context, phone) }) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "Call",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Кнопка WHATSAPP
                IconButton(onClick = { launchWhatsApp(context, phone) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_whatsapp1),
                        contentDescription = "WhatsApp",
                        tint = Color(0xFF25D366), // Фирменный цвет WhatsApp
                                modifier = Modifier.size(24.dp)
                    )
                }

                // Кнопка SMS
                IconButton(onClick = { launchSms(context, phone) }) {
                    Icon(
                        imageVector = Icons.Outlined.MailOutline,
                        contentDescription = "SMS",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    }


@Composable
fun MenuCard(item: MenuItem,
             onClick: () -> Unit
                     )
{
    Card(
        modifier = Modifier
            .aspectRatio(1.1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(AppDimensions.cardCornerRadius),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = stringResource(item.titleRes),
                modifier = Modifier.size(AppDimensions.menuIconSize),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(AppDimensions.paddingSmall))
            Text(
                text = stringResource(item.titleRes),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}