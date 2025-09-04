package com.jusicool.usecase.crypto

import com.jusicool.entity.price.MinuteCandleEntity
import com.jusicool.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetCurrentCryptoMinuteCandleUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {
    operator fun invoke(market: String): Flow<List<MinuteCandleEntity>> {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val formattedNow = now.format(formatter)

        return cryptoRepository.getCurrentMinuteCandle(market = market, to = formattedNow)
    }
}