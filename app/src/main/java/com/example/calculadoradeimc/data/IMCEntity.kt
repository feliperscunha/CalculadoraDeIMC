package com.example.calculadoradeimc.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "imc_table")
data class IMCEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weight: Double,
    val height: Double,
    val imc: Double,
    val classification: String,
    val date: Date,
    val tmb: Double,
    val idealWeight: String,
    val dailyCaloric: String
)
