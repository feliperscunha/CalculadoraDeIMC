package com.example.calculadoradeimc.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.calculadoradeimc.data.repository.MeasurementRepository
import com.example.calculadoradeimc.presentation.screen.DetailScreen
import com.example.calculadoradeimc.presentation.screen.HistoryScreen
import com.example.calculadoradeimc.presentation.screen.HomeScreen
import com.example.calculadoradeimc.presentation.viewmodel.DetailViewModel
import com.example.calculadoradeimc.presentation.viewmodel.HistoryViewModel
import com.example.calculadoradeimc.presentation.viewmodel.HomeViewModel

/**
 * Navigation graph for the application
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    repository: MeasurementRepository
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val viewModel = HomeViewModel(repository)
            HomeScreen(
                viewModel = viewModel,
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                }
            )
        }
        
        composable(Screen.History.route) {
            val viewModel = HistoryViewModel(repository)
            HistoryScreen(
                viewModel = viewModel,
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
            val viewModel = DetailViewModel(repository)
            DetailScreen(
                viewModel = viewModel,
                measurementId = measurementId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
