package com.example.trainly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trainly.presentation.client_charts.ClientChartsScreen
import com.example.trainly.presentation.client_form.ClientFormScreen
import com.example.trainly.presentation.client_list.ClientListScreen
import com.example.trainly.presentation.client_payments.ClientPaymentScreen
import com.example.trainly.presentation.client_photo.ClientPhotoScreen
import com.example.trainly.presentation.client_profile.ClientProfileScreen
import com.example.trainly.presentation.client_routine.ClientRoutineScreen
import com.example.trainly.presentation.client_workouts.ClientWorkoutsScreen
import com.example.trainly.presentation.navigation.DashboardScreen
import com.example.trainly.presentation.navigation.Screen
import com.example.trainly.ui.theme.TrainlyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainlyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    // Set up navigation graph
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Dashboard.route

                    ) {
                        composable(Screen.Dashboard.route) {
                            DashboardScreen(navController)
                        }
                        composable(Screen.ClientList.route) {
                            ClientListScreen(navController)
                        }
                        composable(Screen.ClientProfile.route) { backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
                            ClientProfileScreen(
                                navController = navController,
                                clientId = clientId
                            )
                        }

                        composable (Screen.ClientCharts.route){backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
                            ClientChartsScreen(
                                navController = navController,
                                clientId = clientId,
                                onBack =  { navController.popBackStack() }
                            )

                        }

                        composable (Screen.ClientForm.route){backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
                            ClientFormScreen(
                                navController = navController,
                                clientId = clientId,
                                onBack =  { navController.popBackStack() }
                            )

                        }

                        composable (Screen.ClientPayments.route){backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
                            ClientPaymentScreen(
                                navController = navController,
                                clientId = clientId,
                                onBack =  { navController.popBackStack() }
                            )
                        }

                        composable (Screen.ClientPhoto.route){backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
                            ClientPhotoScreen(
                                navController = navController,
                                clientId = clientId,
                                onBack =  { navController.popBackStack() }
                            )
                        }

                        composable (Screen.ClientRoutine.route){backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
                            ClientRoutineScreen (
                                navController = navController,
                                clientId = clientId,
                                onBack =  { navController.popBackStack() }
                            )
                        }

                        composable (Screen.ClientWorkouts.route){backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
                            ClientWorkoutsScreen(
                                navController = navController,
                                clientId = clientId,
                                onBack =  { navController.popBackStack() }
                            )

                        }

                    }
                }
            }
        }
    }
}