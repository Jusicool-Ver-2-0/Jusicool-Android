package com.jusicool.network.datasource.crypto

import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import com.jusicool.model.crypto.CurrentMinuteCandleResponse
import com.jusicool.model.crypto.MinuteCandleResponse
import com.jusicool.network.api.CryptoApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoDataSourceImpl @Inject constructor(
    private val cryptoApi: CryptoApi
): CryptoDataSource {
    override fun getCurrentCryptoPrice(markets: String): Flow<List<CurrentCryptoPriceResponse>> =
        performApiRequest { cryptoApi.getCurrentCryptoPrice(markets = markets) }

    override fun getMinuteCandle(market: String, to: String, count: Int): Flow<List<MinuteCandleResponse>> =
        performApiRequest { cryptoApi.getMinuteCandle(market = market, to = to, count = count) }

    override fun getCurrentMinuteCandle(market: String, to: String): Flow<List<CurrentMinuteCandleResponse>> =
        performApiRequest { cryptoApi.getCurrentMinuteCandle(market = market, to = to) }
}