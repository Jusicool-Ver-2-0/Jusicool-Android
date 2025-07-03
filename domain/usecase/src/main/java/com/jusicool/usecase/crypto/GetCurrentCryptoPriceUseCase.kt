package com.jusicool.usecase.crypto

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCurrentCryptoPriceUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository,
) {
    operator fun invoke(
        markets: List<String>,
    ): Flow<List<CurrentCryptoPriceModel>> =
        cryptoRepository.getCurrentCryptoPrice(markets)
}
