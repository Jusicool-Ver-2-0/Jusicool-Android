package com.jusicool.network.di

import com.jusicool.network.datasource.account.AccountDataSource
import com.jusicool.network.datasource.account.AccountDataSourceImpl
import com.jusicool.network.datasource.auth.AuthDataSource
import com.jusicool.network.datasource.auth.AuthDataSourceImpl
import com.jusicool.network.datasource.crypto.CryptoDataSource
import com.jusicool.network.datasource.crypto.CryptoDataSourceImpl
import com.jusicool.network.datasource.holding.HoldingDataSource
import com.jusicool.network.datasource.holding.HoldingDataSourceImpl
import com.jusicool.network.datasource.koreaInvestment.KoreaInvestmentDataSource
import com.jusicool.network.datasource.koreaInvestment.KoreaInvestmentDataSourceImpl
import com.jusicool.network.datasource.koreaInvestment.ws.KoreaInvestmentWebSocketManager
import com.jusicool.network.datasource.koreaInvestment.ws.KoreaInvestmentWebSocketManagerInterface
import com.jusicool.network.datasource.market.MarketDataSource
import com.jusicool.network.datasource.market.MarketDataSourceImpl
import com.jusicool.network.datasource.order.OrderDataSource
import com.jusicool.network.datasource.order.OrderDataSourceImpl
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
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun bindAccountDataSource(
        accountDataSourceImpl: AccountDataSourceImpl
    ): AccountDataSource

    @Binds
    abstract fun bindHoldingDataSource(
        holdingDataSourceImpl: HoldingDataSourceImpl
    ): HoldingDataSource

    @Binds
    abstract fun bindCryptoDataSource(
        cryptoDataSourceImpl: CryptoDataSourceImpl
    ): CryptoDataSource

    @Binds
    abstract fun binOrderDataSource(
        orderDataSourceImpl: OrderDataSourceImpl
    ): OrderDataSource

    @Binds
    abstract fun bindKoreaInvestmentDataSource(
        koreaInvestmentDataSourceImpl: KoreaInvestmentDataSourceImpl
    ): KoreaInvestmentDataSource

    @Binds
    abstract fun bindKoreaInvestmentWebSocketManager(
        koreaInvestmentWebSocketManager: KoreaInvestmentWebSocketManager
    ): KoreaInvestmentWebSocketManagerInterface

    @Binds
    abstract fun bindMarketDataSource(
        marketDataSourceImpl: MarketDataSourceImpl
    ): MarketDataSource
}