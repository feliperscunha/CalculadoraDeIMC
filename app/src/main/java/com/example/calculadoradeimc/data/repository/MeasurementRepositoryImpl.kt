package com.example.calculadoradeimc.data.repository

import com.example.calculadoradeimc.data.local.dao.MeasurementDao
import com.example.calculadoradeimc.data.local.entity.toDomainModel
import com.example.calculadoradeimc.data.local.entity.toEntity
import com.example.calculadoradeimc.domain.model.Measurement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of MeasurementRepository using Room database
 */
class MeasurementRepositoryImpl(
    private val measurementDao: MeasurementDao
) : MeasurementRepository {
    
    override fun getAllMeasurements(): Flow<List<Measurement>> {
        return measurementDao.getAllMeasurements()
            .map { entities -> entities.map { it.toDomainModel() } }
    }
    
    override suspend fun getMeasurementById(id: Long): Measurement? {
        return measurementDao.getMeasurementById(id)?.toDomainModel()
    }
    
    override suspend fun getLatestMeasurement(): Measurement? {
        return measurementDao.getLatestMeasurement()?.toDomainModel()
    }
    
    override suspend fun saveMeasurement(measurement: Measurement): Long {
        return measurementDao.insertMeasurement(measurement.toEntity())
    }
    
    override suspend fun updateMeasurement(measurement: Measurement) {
        measurementDao.updateMeasurement(measurement.toEntity())
    }
    
    override suspend fun deleteMeasurement(measurement: Measurement) {
        measurementDao.deleteMeasurement(measurement.toEntity())
    }
    
    override suspend fun deleteAllMeasurements() {
        measurementDao.deleteAllMeasurements()
    }
    
    override fun getMeasurementCount(): Flow<Int> {
        return measurementDao.getMeasurementCount()
    }
}
