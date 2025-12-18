package com.example.calculadoradeimc.di

import android.content.Context
import androidx.room.Room
import com.example.calculadoradeimc.data.IMCDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideIMCDatabase(@ApplicationContext context: Context): IMCDatabase {
        return Room.databaseBuilder(
            context,
            IMCDatabase::class.java,
            "imc_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideIMCDao(database: IMCDatabase) = database.imcDao()

    @Provides
    @Singleton
    fun provideCalculationRepository(imcDao: com.example.calculadoradeimc.data.IMCDao): com.example.calculadoradeimc.domain.repository.CalculationRepository {
        return com.example.calculadoradeimc.data.repository.CalculationRepositoryImpl(imcDao)
    }
}