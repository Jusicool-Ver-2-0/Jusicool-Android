package com.jusicool.usecase.crypto

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.repository.crypto.CryptoRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first


class GetCurrentCryptoPriceUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository,
) {
    suspend operator fun invoke(
        markets: String,
        holdings: List<HoldingModel>
    ): List<CurrentCryptoHoldingPrice> = runCatching {
        val data = cryptoRepository.getCurrentCryptoPrice(markets).first()

        holdings.map { holding ->
            val price = data.find { it.market == holding.marketCode }?.tradePrice ?: 0.0
            val priceVariation = (price - holding.price).toInt()
            val priceVariationPercent = if (holding.price.toDouble() != 0.0) {
                (priceVariation.toDouble() / holding.price) * 100
            } else 0.0

            val totalValue = (price * holding.quantity).toInt()
            val totalVariation = priceVariation * holding.quantity

            CurrentCryptoHoldingPrice(
                marketCode = holding.marketCode,
                currentPrice = price,
                priceVariation = priceVariation,
                priceVariationPercent = priceVariationPercent,
                totalValue = totalValue,
                totalVariation = totalVariation
            )
        }
    }.getOrElse {
        emptyList()
    }
}

data class CurrentCryptoHoldingPrice(
    val marketCode: String,
    val currentPrice: Double,
    val priceVariation: Int,
    val priceVariationPercent: Double,
    val totalValue: Int,
    val totalVariation: Int
)