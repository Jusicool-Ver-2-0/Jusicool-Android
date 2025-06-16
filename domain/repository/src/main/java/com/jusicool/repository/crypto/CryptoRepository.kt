package com.jusicool.repository.crypto

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.entity.crypto.CurrentMinuteCandleModel
import com.jusicool.entity.crypto.MinuteCandleModel
import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import com.jusicool.model.crypto.CurrentMinuteCandleResponse
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getCurrentCryptoPrice(markets: String): Flow<List<CurrentCryptoPriceModel>>

    fun getMinuteCandle(market: String, to: String, count: Int): Flow<List<MinuteCandleModel>>

    fun getCurrentMinuteCandle(market: String, to: String): Flow<List<CurrentMinuteCandleModel>>
}