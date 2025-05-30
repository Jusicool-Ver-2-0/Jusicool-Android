package com.jusicool.network.di

import com.jusicool.network.datasource.auth.AuthDataSource
import com.jusicool.network.datasource.auth.AuthDataSourceImpl
import com.jusicool.network.datasource.chart.ChartDataSource
import com.jusicool.network.datasource.chart.ChartDataSourceImpl
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
    ): ChartDataSource

    @Binds
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource
}