package com.example.calculadoradeimc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.calculadoradeimc.presentation.screen.DetailScreen
import com.example.calculadoradeimc.presentation.screen.HistoryScreen
import com.example.calculadoradeimc.presentation.screen.HomeScreen
import com.example.calculadoradeimc.presentation.viewmodel.ViewModelFactory

/**
 * Navigation graph for the application
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel(factory = viewModelFactory),
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                }
            )
        }
        
        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = viewModel(factory = viewModelFactory),
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { measurementId ->
                    navController.navigate(Screen.Detail.createRoute(measurementId))
                }
            )
        }
        
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("measurementId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val measurementId = backStackEntry.arguments?.getLong("measurementId") ?: 0L
            DetailScreen(
                viewModel = viewModel(factory = viewModelFactory),
                measurementId = measurementId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
