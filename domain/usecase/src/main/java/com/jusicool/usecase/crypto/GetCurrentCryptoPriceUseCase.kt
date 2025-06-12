package com.jusicool.usecase.crypto

import com.jusicool.repository.crypto.CryptoRepository
import javax.inject.Inject

class GetCurrentCryptoPriceUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {
    operator fun invoke(markets: String) = runCatching {
        cryptoRepository.getCurrentCryptoPrice(markets = markets)
    }
}