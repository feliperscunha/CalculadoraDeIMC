package com.example.calculadoradeimc.data.repository

import com.example.calculadoradeimc.data.IMCDao
import com.example.calculadoradeimc.data.IMCEntity
import com.example.calculadoradeimc.domain.repository.CalculationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalculationRepositoryImpl @Inject constructor(
    private val imcDao: IMCDao
) : CalculationRepository {
    override suspend fun insert(imcEntity: IMCEntity) {
        imcDao.insert(imcEntity)
    }

    override fun getAll(): Flow<List<IMCEntity>> {
        return imcDao.getAll()
    }

    override suspend fun getById(id: Int): IMCEntity? {
        return imcDao.getById(id)
    }
}