package com.jusicool.repository

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.model.mapper.crypto.toModel
import com.jusicool.network.datasource.crypto.CryptoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val cryptoDataSource: CryptoDataSource
) : CryptoRepository {
    override fun getCurrentCryptoPrice(markets: List<String>): Flow<List<CurrentCryptoPriceModel>> {
        return cryptoDataSource.getCurrentCryptoPrice(
            markets = markets.joinToString(separator = ",")
        ).map { list ->
            list.map { it.toModel() }
        }
    }

    override fun getMinuteCandle(
        market: String,
        to: String,
        count: Int
    ): Flow<List<MinuteCandleEntity>> {
        return cryptoDataSource.getMinuteCandle(market = market, to = to, count = count)
            .map { list ->
                list.map { it.toModel() }
            }
    }

    override fun getCurrentMinuteCandle(
        market: String,
        to: String
    ): Flow<List<MinuteCandleEntity>> {
        return cryptoDataSource.getCurrentMinuteCandle(market = market, to = to)
            .map { list ->
                list.map { it.toModel() }
            }
    }
}