package com.example.calculadoradeimc.presentation.navigation

/**
 * Sealed class representing all screens in the app
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object History : Screen("history")
    object Detail : Screen("detail/{measurementId}") {
        fun createRoute(measurementId: Long) = "detail/$measurementId"
    }
}
