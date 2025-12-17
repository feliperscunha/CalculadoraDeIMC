package com.example.calculadoradeimc.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadoradeimc.ui.components.HistoricalItem
import com.example.calculadoradeimc.ui.theme.Blue
import com.example.calculadoradeimc.ui.theme.CalculadoraDeIMCTheme
import com.example.calculadoradeimc.ui.theme.White
import com.example.calculadoradeimc.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: HomeViewModel = viewModel()
) {

    val state = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Calculadora de IMC")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White,
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = White)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Altura (cm)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 100.dp, 0.dp, 0.dp)
                )

                Text(
                    text = "Peso (kg)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 100.dp, 20.dp, 0.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = state.height,
                    onValueChange = viewModel::onHeightChange,
                    label = {
                        Text(text = "Ex: 165")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(
                            20.dp,
                            0.dp,
                            0.dp,
                            0.dp
                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = White,
                        focusedContainerColor = White,
                        errorContainerColor = White,
                        focusedLabelColor = Blue,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                    ),
                    isError = state.textFieldError
                )

                OutlinedTextField(
                    value = state.weight,
                    onValueChange = viewModel::onWeightChange,
                    label = {
                        Text(text = "Ex: 70.50")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(
                            0.dp,
                            0.dp,
                            20.dp,
                            0.dp
                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = White,
                        focusedContainerColor = White,
                        errorContainerColor = White,
                        focusedLabelColor = Blue,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                    ),
                    isError = state.textFieldError
                )
            }

            Button(
                onClick = viewModel::onCalculate,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(50.dp)
            ) {
                Text(
                    text = "CALCULAR",
                    fontSize = 18.sp,
                    color = White,
                    fontWeight = FontWeight.Bold,
                )
            }

            Text(
                text = state.resultMessage,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            if (state.history.isNotEmpty()) {
                Text(
                    text = "Histórico",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Adjust height as needed
                ) {
                    items(state.history) { imc ->
                        HistoricalItem(imc = imc)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    CalculadoraDeIMCTheme() {
        Home(viewModel = HomeViewModel(repository = com.example.calculadoradeimc.data.repository.CalculationRepositoryImpl()))
    }
}
