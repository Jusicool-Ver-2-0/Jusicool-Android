package com.jusicool.usecase.crypto

import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMinuteCandleUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {
    operator fun invoke(market: String, to: String, count: Int): Flow<List<MinuteCandleEntity>> =
        cryptoRepository.getMinuteCandle(market = market, to = to, count = count)
}