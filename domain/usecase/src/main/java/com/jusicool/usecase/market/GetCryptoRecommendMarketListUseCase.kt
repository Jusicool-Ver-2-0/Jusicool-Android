package com.jusicool.usecase.market

import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.repository.MarketRepository
import com.jusicool.usecase.crypto.GetCurrentCryptoPriceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCryptoRecommendMarketListUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val getCurrentCryptoPriceUseCase: GetCurrentCryptoPriceUseCase,
) {
    operator fun invoke(): Flow<List<RecommendMarketWithPrice>> =
        marketRepository.getMarketList(MarketType.CRYPTO)
            .flatMapLatest { marketList ->
                getCurrentCryptoPriceUseCase(marketList.map { market -> market.market })
                    .map { currentPriceList ->
                        currentPriceList.mapIndexed { index, data ->
                            RecommendMarketWithPrice(
                                id = index,
                                market = data.market,
                                marketType = MarketType.CRYPTO,
                                koreanName = marketList[index].koreanName,
                                englishName = "",
                                logoUrl = "",
                                currentPrice = data.tradePrice.toLong(),
                                profitRate = 0.0
                            )
                        }
                    }
            }
}