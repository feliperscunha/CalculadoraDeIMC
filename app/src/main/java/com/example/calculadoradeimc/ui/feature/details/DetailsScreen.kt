package com.example.calculadoradeimc.ui.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    id: Int,
    onBack: () -> Unit
) {
    val imc by viewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        viewModel.getIMC(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            imc?.let {
                Text(text = "Peso: ${it.weight}")
                Text(text = "Altura: ${it.height}")
                Text(text = "IMC: ${it.imc}")
                Text(text = "Classificação: ${it.classification}")
                Text(text = "Data: ${it.date}")
                Text(text = "TMB: ${it.bmr}")
                Text(text = "Peso Ideal: ${it.idealWeight}")
                Text(text = "Necessidade Calórica Diária: ${it.dailyCaloricNeed}")
            }
        }
    }
}
