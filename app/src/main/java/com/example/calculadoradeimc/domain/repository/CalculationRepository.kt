package com.example.calculadoradeimc.domain.repository

import com.example.calculadoradeimc.domain.imcData

interface CalculationRepository {
    fun getHistory(): List<imcData>
    fun calculateImc(weight: String, height: String): imcData?
    fun addImcToHistory(imc: imcData)
}
