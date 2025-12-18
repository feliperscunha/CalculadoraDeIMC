package com.example.calculadoradeimc.ui.feature.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.domain.repository.CalculationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    repository: CalculationRepository
) : ViewModel() {

    val history = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
