package com.jusicool.usecase.crypto

import com.jusicool.repository.crypto.CryptoRepository
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetCurrentMinuteCandleUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {
    operator fun invoke(market: String) = runCatching {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val formattedNow = now.format(formatter)
        cryptoRepository.getCurrentMinuteCandle(market = market, to = formattedNow)
    }
}