package com.jusicool.usecase.market.util

import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.entity.koreaInvestment.AssetsCurrentPrice
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
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
                currentPrice = it.currentPrice.toLong(),
                profitRate = it.priceDifferenceRate
            )
        }
    }

fun List<Market>.mergeWithPrices(
    prices: List<CurrentCryptoPriceModel>
): List<RecommendMarketWithPrice> =
    mapNotNull { market ->
        val priceInfo = prices.find { it.market == market.market }

        priceInfo?.let {
            RecommendMarketWithPrice(
                id = market.id,
                market = market.market,
                marketType = MarketType.CRYPTO,
                koreanName = market.koreanName,
                englishName = market.englishName,
                logoUrl = null,
                currentPrice = it.tradePrice.toLong(),
                profitRate = 0.0 // TODO: 계산 필요 시 여기에 반영
            )
        }
    }