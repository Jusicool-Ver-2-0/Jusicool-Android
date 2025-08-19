package com.jusicool.repository

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.entity.crypto.MinuteCandleModel
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getCurrentCryptoPrice(markets: List<String>): Flow<List<CurrentCryptoPriceModel>>

    fun getMinuteCandle(market: String, to: String, count: Int): Flow<List<MinuteCandleModel>>

    fun getCurrentMinuteCandle(market: String, to: String): Flow<List<MinuteCandleModel>>
}