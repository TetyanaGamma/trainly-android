package com.example.trainly.presentation.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object ClientList : Screen("client_list")
    object ClientProfile : Screen("client_profile/{clientId}") {
        fun createRoute(clientId: String) = "client_profile/$clientId"
    }
    object ClientRoutine : Screen("client_routine/{clientId}") {
        fun createRoute(clientId: String) = "client_routine/$clientId"
    }
    object ClientCharts : Screen("client_charts/{clientId}") {
        fun createRoute(clientId: String) = "client_charts/$clientId"
    }
    object ClientWorkouts : Screen("client_workouts/{clientId}") {
        fun createRoute(clientId: String) = "client_workouts/$clientId"
    }
    object ClientForm : Screen("client_form/{clientId}") {
        fun createRoute(clientId: String) = "client_form/$clientId"
    }
    object ClientPayments : Screen("client_payments/{clientId}") {
        fun createRoute(clientId: String) = "client_payments/$clientId"
    }
    object ClientPhoto: Screen("client_photo/{clientId}") {
        fun createRoute(clientId: String) = "client_photo/$clientId"
    }
}