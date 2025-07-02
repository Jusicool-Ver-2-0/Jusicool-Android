package com.jusicool.di

import com.jusicool.repository.AccountRepository
import com.jusicool.repository.AccountRepositoryImpl
import com.jusicool.repository.AuthRepository
import com.jusicool.repository.AuthRepositoryImpl
import com.jusicool.repository.CryptoRepository
import com.jusicool.repository.CryptoRepositoryImpl
import com.jusicool.repository.HoldingRepository
import com.jusicool.repository.HoldingRepositoryImpl
import com.jusicool.repository.KoreaInvestmentRepository
import com.jusicool.repository.KoreaInvestmentRepositoryImpl
import com.jusicool.repository.MarketRepository
import com.jusicool.repository.MarketRepositoryImpl
import com.jusicool.repository.OrderRepository
import com.jusicool.repository.OrderRepositoryImpl
import com.jusicool.repository.WsKoreaInvestmentRepository
import com.jusicool.repository.WsKoreaInvestmentRepositoryImpl
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
    abstract fun bindKoreaInvestmentRepository(
        koreaInvestmentRepositoryImpl: KoreaInvestmentRepositoryImpl
    ): KoreaInvestmentRepository

    @Binds
    abstract fun bindWsKoreaInvestmentRepository(
        wsKoreaInvestmentRepositoryImpl: WsKoreaInvestmentRepositoryImpl
    ): WsKoreaInvestmentRepository

    @Binds
    abstract fun binOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    abstract fun bindMarketRepository(
        marketRepositoryImpl: MarketRepositoryImpl
    ): MarketRepository
}