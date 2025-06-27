package com.jusicool.di

import com.jusicool.local.JusicoolDataBase
import com.jusicool.local.dao.HoldingDao
import com.jusicool.local.dao.MarketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideMarketDao(database: JusicoolDataBase): MarketDao {
        return database.marketDao()
    }

    @Provides
    fun provideHoldingDao(database: JusicoolDataBase): HoldingDao {
        return database.holdingDao()
    }
}