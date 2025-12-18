package com.example.calculadoradeimc.domain.repository

import com.example.calculadoradeimc.data.IMCEntity
import kotlinx.coroutines.flow.Flow

interface CalculationRepository {
    suspend fun insert(imcEntity: IMCEntity)
    fun getAll(): Flow<List<IMCEntity>>
    suspend fun getById(id: Long): IMCEntity?
}