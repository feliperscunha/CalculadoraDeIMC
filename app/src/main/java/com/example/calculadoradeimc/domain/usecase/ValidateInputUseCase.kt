package com.example.calculadoradeimc.domain.usecase

import com.example.calculadoradeimc.domain.model.ValidationResult

/**
 * Use case for validating user inputs
 * 
 * Ensures all measurements are within reasonable ranges:
 * - Weight: 20-300 kg
 * - Height: 50-250 cm
 * - Age: 1-120 years
 * - Circumferences: 10-200 cm
 */
class ValidateInputUseCase {
    
    fun validateWeight(weight: String): ValidationResult {
        if (weight.isEmpty()) {
            return ValidationResult.Invalid("Peso é obrigatório")
        }
        
        val weightValue = weight.replace(",", ".").toDoubleOrNull()
            ?: return ValidationResult.Invalid("Peso inválido")
        
        return when {
            weightValue <= 0 -> ValidationResult.Invalid("Peso deve ser maior que zero")
            weightValue < 20 -> ValidationResult.Invalid("Peso muito baixo (mínimo: 20 kg)")
            weightValue > 300 -> ValidationResult.Invalid("Peso muito alto (máximo: 300 kg)")
            else -> ValidationResult.Valid
        }
    }
    
    fun validateHeight(height: String): ValidationResult {
        if (height.isEmpty()) {
            return ValidationResult.Invalid("Altura é obrigatória")
        }
        
        val heightValue = height.toDoubleOrNull()
            ?: return ValidationResult.Invalid("Altura inválida")
        
        return when {
            heightValue <= 0 -> ValidationResult.Invalid("Altura deve ser maior que zero")
            heightValue < 50 -> ValidationResult.Invalid("Altura muito baixa (mínimo: 50 cm)")
            heightValue > 250 -> ValidationResult.Invalid("Altura muito alta (máximo: 250 cm)")
            else -> ValidationResult.Valid
        }
    }
    
    fun validateAge(age: String): ValidationResult {
        if (age.isEmpty()) {
            return ValidationResult.Invalid("Idade é obrigatória")
        }
        
        val ageValue = age.toIntOrNull()
            ?: return ValidationResult.Invalid("Idade inválida")
        
        return when {
            ageValue <= 0 -> ValidationResult.Invalid("Idade deve ser maior que zero")
            ageValue < 1 -> ValidationResult.Invalid("Idade muito baixa")
            ageValue > 120 -> ValidationResult.Invalid("Idade muito alta (máximo: 120 anos)")
            else -> ValidationResult.Valid
        }
    }
    
    fun validateCircumference(value: String, fieldName: String): ValidationResult {
        if (value.isEmpty()) {
            return ValidationResult.Invalid("$fieldName é obrigatória")
        }
        
        val circumference = value.replace(",", ".").toDoubleOrNull()
            ?: return ValidationResult.Invalid("$fieldName inválida")
        
        return when {
            circumference <= 0 -> ValidationResult.Invalid("$fieldName deve ser maior que zero")
            circumference < 10 -> ValidationResult.Invalid("$fieldName muito baixa (mínimo: 10 cm)")
            circumference > 200 -> ValidationResult.Invalid("$fieldName muito alta (máximo: 200 cm)")
            else -> ValidationResult.Valid
        }
    }
}
