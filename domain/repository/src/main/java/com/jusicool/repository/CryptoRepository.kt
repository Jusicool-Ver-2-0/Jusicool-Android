package com.jusicool.repository

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.entity.price.MinuteCandleEntity
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getCurrentCryptoPrice(markets: List<String>): Flow<List<AssetsCurrentPrice>>

    fun getMinuteCandle(market: String, to: String, count: Int): Flow<List<MinuteCandleEntity>>

    fun getCurrentMinuteCandle(market: String, to: String): Flow<List<MinuteCandleEntity>>
}