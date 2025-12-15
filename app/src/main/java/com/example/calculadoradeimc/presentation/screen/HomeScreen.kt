package com.example.calculadoradeimc.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradeimc.domain.model.ActivityLevel
import com.example.calculadoradeimc.domain.model.Gender
import com.example.calculadoradeimc.domain.usecase.*
import com.example.calculadoradeimc.presentation.components.InputField
import com.example.calculadoradeimc.presentation.components.MetricCard
import com.example.calculadoradeimc.presentation.viewmodel.HomeViewModel
import com.example.calculadoradeimc.ui.theme.Blue
import com.example.calculadoradeimc.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToHistory: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    // Use cases for interpretations
    val calculateBMI = remember { CalculateBMIUseCase() }
    val calculateBMR = remember { CalculateBMRUseCase() }
    val calculateBodyFat = remember { CalculateBodyFatUseCase() }
    val calculateIdealWeight = remember { CalculateIdealWeightUseCase() }
    val calculateDailyCaloricNeeds = remember { CalculateDailyCaloricNeedsUseCase() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calculadora de Saúde") },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Histórico"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White,
                    actionIconContentColor = White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = White)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Basic measurements section
            Text(
                text = "Medidas Básicas (Obrigatórias)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InputField(
                    value = uiState.height,
                    onValueChange = { viewModel.onHeightChanged(it) },
                    label = "Altura (cm)",
                    placeholder = "Ex: 165",
                    isError = uiState.heightError != null,
                    errorMessage = uiState.heightError,
                    keyboardType = KeyboardType.Number,
                    maxLength = 3,
                    modifier = Modifier.weight(1f)
                )
                
                InputField(
                    value = uiState.weight,
                    onValueChange = { viewModel.onWeightChanged(it) },
                    label = "Peso (kg)",
                    placeholder = "Ex: 70.5",
                    isError = uiState.weightError != null,
                    errorMessage = uiState.weightError,
                    keyboardType = KeyboardType.Decimal,
                    maxLength = 7,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Optional measurements section
            Text(
                text = "Dados Adicionais (Opcionais)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            InputField(
                value = uiState.age,
                onValueChange = { viewModel.onAgeChanged(it) },
                label = "Idade (anos)",
                placeholder = "Ex: 30",
                isError = uiState.ageError != null,
                errorMessage = uiState.ageError,
                keyboardType = KeyboardType.Number,
                maxLength = 3,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Gender selection
            Text(
                text = "Sexo:",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.gender == Gender.MALE,
                    onClick = { viewModel.onGenderChanged(Gender.MALE) },
                    label = { Text("Masculino") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = uiState.gender == Gender.FEMALE,
                    onClick = { viewModel.onGenderChanged(Gender.FEMALE) },
                    label = { Text("Feminino") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Circumferences for body fat calculation
            Text(
                text = "Circunferências (para % de gordura):",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InputField(
                    value = uiState.waistCircumference,
                    onValueChange = { viewModel.onWaistChanged(it) },
                    label = "Cintura (cm)",
                    placeholder = "Ex: 80",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
                
                InputField(
                    value = uiState.neckCircumference,
                    onValueChange = { viewModel.onNeckChanged(it) },
                    label = "Pescoço (cm)",
                    placeholder = "Ex: 35",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.weight(1f)
                )
            }
            
            if (uiState.gender == Gender.FEMALE) {
                Spacer(modifier = Modifier.height(8.dp))
                InputField(
                    value = uiState.hipCircumference,
                    onValueChange = { viewModel.onHipChanged(it) },
                    label = "Quadril (cm) - Obrigatório para mulheres",
                    placeholder = "Ex: 95",
                    keyboardType = KeyboardType.Decimal,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Activity level selection
            Text(
                text = "Nível de Atividade Física:",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            ActivityLevel.values().forEach { level ->
                FilterChip(
                    selected = uiState.activityLevel == level,
                    onClick = { viewModel.onActivityLevelChanged(level) },
                    label = { 
                        Text(
                            text = level.description,
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Calculate button
            Button(
                onClick = { viewModel.calculateMetrics() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "CALCULAR",
                    fontSize = 18.sp,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Results section
            uiState.calculationResult?.let { measurement ->
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Resultados",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // BMI (always present)
                MetricCard(
                    title = "IMC (Índice de Massa Corporal)",
                    value = String.format("%.2f - %s", measurement.bmi, measurement.bmiClassification),
                    interpretation = calculateBMI.getInterpretation(measurement.bmi),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                // BMR (if available)
                measurement.bmr?.let { bmr ->
                    MetricCard(
                        title = "TMB (Taxa Metabólica Basal)",
                        value = String.format("%.0f calorias/dia", bmr),
                        interpretation = calculateBMR.getInterpretation(bmr),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                
                // Ideal Weight (if available)
                measurement.idealWeight?.let { idealWeight ->
                    MetricCard(
                        title = "Peso Ideal",
                        value = String.format("%.1f kg", idealWeight),
                        interpretation = calculateIdealWeight.getInterpretation(measurement.weight, idealWeight),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                
                // Body Fat (if available)
                if (measurement.bodyFatPercentage != null && measurement.gender != null) {
                    MetricCard(
                        title = "Percentual de Gordura Corporal",
                        value = String.format(
                            "%.1f%% - %s",
                            measurement.bodyFatPercentage,
                            calculateBodyFat.classify(measurement.bodyFatPercentage, measurement.gender)
                        ),
                        interpretation = calculateBodyFat.getInterpretation(
                            measurement.bodyFatPercentage,
                            measurement.gender
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                
                // Daily Caloric Needs (if available)
                measurement.dailyCaloricNeeds?.let { calories ->
                    MetricCard(
                        title = "Necessidade Calórica Diária",
                        value = String.format("%.0f calorias/dia", calories),
                        interpretation = calculateDailyCaloricNeeds.getInterpretation(calories),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
