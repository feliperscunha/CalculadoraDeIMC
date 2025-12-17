package com.example.calculadoradeimc.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculadoradeimc.domain.repository.CalculationRepository
import com.example.calculadoradeimc.ui.feature.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CalculationRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        uiState = uiState.copy(history = repository.getHistory())
    }

    fun onHeightChange(value: String) {
        if (value.length <= 3)
            uiState = uiState.copy(height = value)
    }

    fun onWeightChange(value: String) {
        if (value.length <= 7)
            uiState = uiState.copy(weight = value)
    }

    fun onCalculate() {
        val imcResult = repository.calculateImc(
            weight = uiState.weight,
            height = uiState.height
        )

        if (imcResult == null) {
            uiState = uiState.copy(
                resultMessage = "Preencha todos os campos!",
                textFieldError = true
            )
        } else {
            repository.addImcToHistory(imcResult)
            uiState = uiState.copy(
                resultMessage = "IMC: ${imcResult.result}\n${imcResult.classification}",
                textFieldError = false,
                history = repository.getHistory()
            )
        }
    }
}
