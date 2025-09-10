package com.jusicool.usecase.market.util

import com.jusicool.entity.price.AssetsCurrentPrice
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.RecommendMarketWithPrice

/**
 * [Market] 리스트와 [AssetsCurrentPrice] 리스트를 병합하여
 * [RecommendMarketWithPrice] 리스트로 변환하는 확장 함수입니다.
 *
 * 각 마켓의 market 코드(market.market)를 기준으로 price 정보와 매칭합니다.
 */

fun List<Market>.mergeWithPrices(
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
                currentPrice = it.currentPrice,
                profitRate = it.priceDifferenceRate
            )
        }
    }
