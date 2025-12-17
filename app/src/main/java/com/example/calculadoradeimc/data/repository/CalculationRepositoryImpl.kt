package com.example.calculadoradeimc.data.repository

import com.example.calculadoradeimc.domain.imcData
import com.example.calculadoradeimc.domain.repository.CalculationRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CalculationRepositoryImpl @Inject constructor() : CalculationRepository {

    private val history = mutableListOf<imcData>()

    override fun getHistory(): List<imcData> {
        return history.toList()
    }

    override fun calculateImc(weight: String, height: String): imcData? {
        val weightFormatted = weight.replace(",", ".").toDoubleOrNull()
        val heightFormatted = height.toDoubleOrNull()

        if (weightFormatted != null && heightFormatted != null && heightFormatted > 0) {
            val imcValue = weightFormatted / ((heightFormatted / 100) * (heightFormatted / 100))
            val classification = getClassification(imcValue)
            val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

            return imcData(
                result = String.format(Locale.US, "%.2f", imcValue),
                classification = classification,
                date = currentDate
            )
        }
        return null
    }

    override fun addImcToHistory(imc: imcData) {
        history.add(0, imc) // Adiciona no início da lista
    }

    private fun getClassification(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do Peso"
            imc in 18.5..24.9 -> "Peso Normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc in 30.0..34.9 -> "Obesidade (Grau 1)"
            imc in 35.0..39.9 -> "Obesidade Severa (Grau 2)"
            else -> "Obesidade Mórbida (Grau 3)"
        }
    }
}
