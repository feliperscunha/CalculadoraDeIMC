package com.example.calculadoradeimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.calculadoradeimc.data.local.database.AppDatabase
import com.example.calculadoradeimc.data.repository.MeasurementRepositoryImpl
import com.example.calculadoradeimc.presentation.navigation.NavGraph
import com.example.calculadoradeimc.ui.theme.CalculadoraDeIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initialize database and repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = MeasurementRepositoryImpl(database.measurementDao())
        
        setContent {
            CalculadoraDeIMCTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    repository = repository
                )
            }
        }
    }
}