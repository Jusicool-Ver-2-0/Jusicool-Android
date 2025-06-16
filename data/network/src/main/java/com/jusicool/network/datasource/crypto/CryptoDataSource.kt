package com.jusicool.network.datasource.crypto

import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import com.jusicool.model.crypto.CurrentMinuteCandleResponse
import com.jusicool.model.crypto.MinuteCandleResponse
import kotlinx.coroutines.flow.Flow

interface CryptoDataSource {
    fun getCurrentCryptoPrice(markets: String): Flow<List<CurrentCryptoPriceResponse>>

    fun getMinuteCandle(market: String, to: String, count: Int): Flow<List<MinuteCandleResponse>>

    fun getCurrentMinuteCandle(market: String, to: String): Flow<List<CurrentMinuteCandleResponse>>
}