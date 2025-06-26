package com.jusicool.di

import com.jusicool.repository.MarketRepository
import com.jusicool.repository.MarketRepositoryImpl
import com.jusicool.repository.account.AccountRepository
import com.jusicool.repository.account.AccountRepositoryImpl
import com.jusicool.repository.auth.AuthRepository
import com.jusicool.repository.auth.AuthRepositoryImpl
import com.jusicool.repository.crypto.CryptoRepository
import com.jusicool.repository.crypto.CryptoRepositoryImpl
import com.jusicool.repository.holding.HoldingRepository
import com.jusicool.repository.holding.HoldingRepositoryImpl
import com.jusicool.repository.order.OrderRepository
import com.jusicool.repository.order.OrderRepositoryImpl
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

    @Binds
    abstract fun bindMarketRepository(
        marketRepositoryImpl: MarketRepositoryImpl
    ): MarketRepository
}