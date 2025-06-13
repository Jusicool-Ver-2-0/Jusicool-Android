package com.jusicool.network.datasource.crypto

import com.jusicool.model.chart.ChartResponse
import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import kotlinx.coroutines.flow.Flow

interface CryptoDataSource {
    fun getCurrentCryptoPrice(markets: String): Flow<List<CurrentCryptoPriceResponse>>
}