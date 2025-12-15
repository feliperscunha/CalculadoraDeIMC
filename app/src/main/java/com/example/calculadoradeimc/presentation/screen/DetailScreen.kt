package com.example.calculadoradeimc.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradeimc.presentation.components.MetricCard
import com.example.calculadoradeimc.presentation.viewmodel.DetailUiState
import com.example.calculadoradeimc.presentation.viewmodel.DetailViewModel
import com.example.calculadoradeimc.ui.theme.Blue
import com.example.calculadoradeimc.ui.theme.White
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    measurementId: Long,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    LaunchedEffect(measurementId) {
        viewModel.loadMeasurement(measurementId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalhes da Medição") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Blue)
                }
            }
            
            is DetailUiState.Success -> {
                val measurement = state.measurement
                val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault()) }
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = White)
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    // Header with date
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Blue.copy(alpha = 0.1f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = dateFormat.format(measurement.timestamp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Blue
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Basic measurements
                    Text(
                        text = "Medidas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = "Peso:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                    Text(text = "${String.format("%.1f", measurement.weight)} kg", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "Altura:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                    Text(text = "${String.format("%.0f", measurement.height)} cm", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            
                            measurement.age?.let { age ->
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(text = "Idade:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                        Text(text = "$age anos", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    }
                                    measurement.gender?.let { gender ->
                                        Column(horizontalAlignment = Alignment.End) {
                                            Text(text = "Sexo:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                            Text(
                                                text = if (gender.name == "MALE") "Masculino" else "Feminino",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Health metrics
                    Text(
                        text = "Indicadores de Saúde",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    // BMI
                    MetricCard(
                        title = "IMC (Índice de Massa Corporal)",
                        value = String.format("%.2f - %s", measurement.bmi, measurement.bmiClassification),
                        interpretation = state.bmiInterpretation,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    // BMR
                    state.bmrInterpretation?.let { interpretation ->
                        measurement.bmr?.let { bmr ->
                            MetricCard(
                                title = "TMB (Taxa Metabólica Basal)",
                                value = String.format("%.0f calorias/dia", bmr),
                                interpretation = interpretation,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }
                    
                    // Ideal Weight
                    state.idealWeightInterpretation?.let { interpretation ->
                        measurement.idealWeight?.let { idealWeight ->
                            MetricCard(
                                title = "Peso Ideal",
                                value = String.format("%.1f kg", idealWeight),
                                interpretation = interpretation,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }
                    
                    // Body Fat
                    state.bodyFatInterpretation?.let { interpretation ->
                        measurement.bodyFatPercentage?.let { bodyFat ->
                            MetricCard(
                                title = "Percentual de Gordura Corporal",
                                value = String.format("%.1f%%", bodyFat),
                                interpretation = interpretation,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }
                    
                    // Daily Caloric Needs
                    state.dailyCaloricInterpretation?.let { interpretation ->
                        measurement.dailyCaloricNeeds?.let { calories ->
                            MetricCard(
                                title = "Necessidade Calórica Diária",
                                value = String.format("%.0f calorias/dia", calories),
                                interpretation = interpretation,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Erro ao carregar medição",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
