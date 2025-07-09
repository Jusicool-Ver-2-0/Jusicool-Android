package com.jusicool.usecase.market

import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.market.RecommendMarketWithPrice
import com.jusicool.repository.MarketRepository
import com.jusicool.usecase.koreaInvestment.GetCurrentStockPriceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 주식 마켓 목록과 실시간 주가 데이터를 결합하여
 * 추천 마켓 정보([RecommendMarketWithPrice]) 리스트를 제공하는 유스케이스입니다.
 */
class GetStockRecommendMarketListUseCase @Inject constructor(
    private val marketRepository: MarketRepository,
    private val getCurrentStockPriceUseCase: GetCurrentStockPriceUseCase,
) {
    /**
     * 주식 마켓 리스트를 가져오고, 각 마켓의 현재가 정보를 받아
     * [RecommendMarketWithPrice] 리스트로 변환합니다.
     */
    operator fun invoke(): Flow<List<RecommendMarketWithPrice>> {
        return marketRepository.getMarketList(MarketType.STOCK)
            .flatMapLatest { markets ->
                val marketCodes = markets.map { it.market }

                getCurrentStockPriceUseCase(marketCodes)
                    .map { prices -> markets.mergeWithPrices(prices) }
            }
    }
}

/**
 * [Market] 리스트와 [AssetsCurrentPrice] 리스트를 병합하여
 * [RecommendMarketWithPrice] 리스트로 변환하는 확장 함수입니다.
 *
 * 각 마켓의 market 코드(market.market)를 기준으로 price 정보와 매칭합니다.
 */
private fun List<Market>.mergeWithPrices(
    prices: List<AssetsCurrentPrice>
): List<RecommendMarketWithPrice> =
    mapNotNull { market ->
        val priceInfo = prices.find { it.market == market.market }

        priceInfo?.let {
            RecommendMarketWithPrice(
                id = market.id,
                market = market.market,
                marketType = market.marketType,
                koreanName = market.koreanName,
                englishName = market.englishName,
                logoUrl = null, // TODO: 필요 시 매핑
                currentPrice = it.currentPrice.toLong(),
                profitRate = it.priceDifferenceRate
            )
        }
    }