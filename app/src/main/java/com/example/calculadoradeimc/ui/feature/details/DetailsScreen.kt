package com.example.calculadoradeimc.ui.feature.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calculadoradeimc.data.IMCEntity
import com.example.calculadoradeimc.ui.theme.Blue
import com.example.calculadoradeimc.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    id: Long,
    onBack: () -> Unit
) {
    val imc by viewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        viewModel.getIMC(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do IMC") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White,
                    navigationIconContentColor = White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imc?.let {
                DetailsCard(it)
            }
        }
    }
}

@Composable
fun DetailsCard(imc: IMCEntity) {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        DetailItem(label = "Data", value = sdf.format(imc.date))
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailItem(label = "Peso", value = "${imc.weight} kg")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailItem(label = "Altura", value = "${imc.height} cm")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailItem(label = "IMC", value = "%.2f".format(imc.imc))
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailItem(label = "Classificação", value = imc.classification)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailItem(label = "TMB", value = "%.2f".format(imc.tmb) + " kcal")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailItem(label = "Peso Ideal", value = imc.idealWeight)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DetailItem(label = "Necessidade Calórica Diária", value = imc.dailyCaloric)
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}