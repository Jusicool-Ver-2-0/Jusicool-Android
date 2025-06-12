package com.jusicool.repository.crypto

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {
    fun getCurrentCryptoPrice(markets: String): Flow<List<CurrentCryptoPriceModel>>
}