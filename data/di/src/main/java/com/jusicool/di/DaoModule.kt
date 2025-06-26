package com.jusicool.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
//    @Provides
//    fun provideStockDao(database: AppDatabase): StockDao {
//        return database.stockDao()
//    }
}