package com.jusicool.network.datasource.crypto

import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import com.jusicool.network.api.CryptoApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CryptoDataSourceImpl @Inject constructor(
    private val cryptoApi: CryptoApi
): CryptoDataSource {
    override fun getCurrentCryptoPrice(markets: String): Flow<List<CurrentCryptoPriceResponse>> =
        performApiRequest { cryptoApi.getCurrentCryptoPrice(markets = markets) }
}