package com.example.calculadoradeimc.ui.feature

import com.example.calculadoradeimc.domain.imcData

data class HomeUiState(
    val height: String = "",
    val weight: String = "",
    val resultMessage: String = "",
    val textFieldError: Boolean = false,
    val history: List<imcData> = emptyList()
)
