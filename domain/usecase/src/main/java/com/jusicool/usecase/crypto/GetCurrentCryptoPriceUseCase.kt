package com.jusicool.usecase.crypto

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.repository.CryptoRepository
import com.jusicool.repository.WsUpbitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentCryptoPriceUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository,
    private val wsUpbitRepository: WsUpbitRepository
) {
    operator fun invoke(markets: List<String>): Flow<List<AssetsCurrentPrice>> = flow {
        val initial = cryptoRepository.getCurrentCryptoPrice(markets).first()
        emit(initial)
        emitAll(wsUpbitRepository.observeTicker(markets))
    }
}
