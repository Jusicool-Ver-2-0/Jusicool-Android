package com.jusicool.di

import com.jusicool.repository.AccountRepository
import com.jusicool.repository.AccountRepositoryImpl
import com.jusicool.repository.AuthRepository
import com.jusicool.repository.AuthRepositoryImpl
import com.jusicool.repository.CryptoRepository
import com.jusicool.repository.CryptoRepositoryImpl
import com.jusicool.repository.HoldingRepository
import com.jusicool.repository.HoldingRepositoryImpl
import com.jusicool.repository.OrderRepository
import com.jusicool.repository.OrderRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    // todo : Add Repository Instance

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    abstract fun bindHoldingRepository(
        holdingRepositoryImpl: HoldingRepositoryImpl
    ): HoldingRepository

    @Binds
    abstract fun bindCryptoRepository(
        cryptoRepositoryImpl: CryptoRepositoryImpl
    ): CryptoRepository

    @Binds
    abstract fun binOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}