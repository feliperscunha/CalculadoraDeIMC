package com.example.calculadoradeimc.ui.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.data.IMCEntity
import com.example.calculadoradeimc.domain.repository.CalculationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: CalculationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<IMCEntity?>(null)
    val uiState: StateFlow<IMCEntity?> = _uiState

    fun getIMC(id: Long) {
        viewModelScope.launch {
            _uiState.value = repository.getById(id)
        }
    }
}
