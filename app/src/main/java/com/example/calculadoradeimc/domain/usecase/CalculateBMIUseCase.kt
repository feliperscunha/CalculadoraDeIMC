package com.example.calculadoradeimc.domain.usecase

/**
 * Use case for calculating Body Mass Index (BMI)
 * 
 * Formula: BMI = weight (kg) / height² (m²)
 * 
 * Classifications (WHO):
 * - Below 18.5: Underweight (Abaixo do Peso)
 * - 18.5 - 24.9: Normal weight (Peso Normal)
 * - 25.0 - 29.9: Overweight (Sobrepeso)
 * - 30.0 - 34.9: Obesity Class I (Obesidade Grau 1)
 * - 35.0 - 39.9: Obesity Class II (Obesidade Severa - Grau 2)
 * - >= 40.0: Obesity Class III (Obesidade Mórbida - Grau 3)
 */
class CalculateBMIUseCase {
    
    /**
     * Calculate BMI from weight and height
     * @param weight Weight in kilograms
     * @param height Height in centimeters
     * @return Pair of BMI value and classification
     */
    operator fun invoke(weight: Double, height: Double): Pair<Double, String> {
        val heightInMeters = height / 100.0
        val bmi = weight / (heightInMeters * heightInMeters)
        val classification = classifyBMI(bmi)
        return Pair(bmi, classification)
    }
    
    private fun classifyBMI(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Abaixo do Peso"
            bmi < 25.0 -> "Peso Normal"
            bmi < 30.0 -> "Sobrepeso"
            bmi < 35.0 -> "Obesidade (Grau 1)"
            bmi < 40.0 -> "Obesidade Severa (Grau 2)"
            else -> "Obesidade Mórbida (Grau 3)"
        }
    }
    
    /**
     * Get detailed interpretation of BMI classification
     */
    fun getInterpretation(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Você está abaixo do peso ideal. Considere consultar um nutricionista para ganhar peso de forma saudável."
            bmi < 25.0 -> "Parabéns! Você está com peso normal. Continue mantendo hábitos saudáveis."
            bmi < 30.0 -> "Você está com sobrepeso. Considere adotar uma dieta equilibrada e praticar exercícios regularmente."
            bmi < 35.0 -> "Obesidade Grau 1. É importante buscar orientação médica para estabelecer um plano de emagrecimento."
            bmi < 40.0 -> "Obesidade Severa (Grau 2). Procure um médico para avaliação e tratamento adequado."
            else -> "Obesidade Mórbida (Grau 3). É fundamental buscar acompanhamento médico imediato para sua saúde."
        }
    }
}
