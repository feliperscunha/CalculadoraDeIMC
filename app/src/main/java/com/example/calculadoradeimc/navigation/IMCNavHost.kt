package com.example.calculadoradeimc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calculadoradeimc.ui.feature.Home
import com.example.calculadoradeimc.ui.feature.details.DetailsScreen
import com.example.calculadoradeimc.ui.feature.historic.HistoryScreen

@Composable
fun IMCNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(
                onHistoryClick = {
                    navController.navigate("history")
                }
            )
        }
        composable("history") {
            HistoryScreen(
                onItemClick = { id ->
                    navController.navigate("details/$id")
                }
            )
        }
        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            DetailsScreen(
                id = backStackEntry.arguments?.getInt("id") ?: 0,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
