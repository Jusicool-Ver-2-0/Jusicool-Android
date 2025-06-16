package com.jusicool.usecase.crypto

import com.jusicool.repository.CryptoRepository
import javax.inject.Inject

class GetMinuteCandleUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {
    operator fun invoke(market: String, to: String, count: Int) = runCatching {
        cryptoRepository.getMinuteCandle(market = market, to = to, count = count)
    }
}