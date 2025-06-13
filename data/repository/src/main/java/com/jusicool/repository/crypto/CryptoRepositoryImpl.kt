package com.jusicool.repository.crypto

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.model.mapper.crypto.toModel
import com.jusicool.network.datasource.crypto.CryptoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val cryptoDataSource: CryptoDataSource
):CryptoRepository {
    override fun getCurrentCryptoPrice(markets: String): Flow<List<CurrentCryptoPriceModel>> {
        return cryptoDataSource.getCurrentCryptoPrice(markets = markets).map { list -> list.map{ it.toModel() } }
    }
}