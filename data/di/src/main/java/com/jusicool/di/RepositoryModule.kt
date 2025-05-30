package com.jusicool.di

import com.jusicool.repository.auth.AuthRepository
import com.jusicool.repository.auth.AuthRepositoryImpl
import com.jusicool.repository.chart.ChartRepositoryImpl
import com.jusicool.repository.chart.ChartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // todo : Add Repository Instance
    @Binds
    abstract fun bindChartRepository(
        chartRepositoryImpl: ChartRepositoryImpl
    ): ChartRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}