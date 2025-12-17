package com.example.calculadoradeimc.domain

data class imcData(

    val id: Long,
    val height: Int,
    val weight: Double,
    val imc: Double,
    val resultMessage: String,
)
