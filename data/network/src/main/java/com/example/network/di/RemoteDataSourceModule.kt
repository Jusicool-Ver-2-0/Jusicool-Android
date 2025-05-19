package com.example.network.di

import com.example.network.datasource.chart.ChartDataSource
import com.example.network.datasource.chart.ChartDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
/*
    @Binds
    abstract fun bindAuthRemoteDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ) : AuthDataSource
*/

    @Binds
    abstract fun bindChartDataSource(
        chartDataSourceImpl: ChartDataSourceImpl
    ):ChartDataSource
}