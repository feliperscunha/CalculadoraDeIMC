package com.example.calculadoradeimc.domain.model

/**
 * Result of input validation
 */
sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val message: String) : ValidationResult()
}
