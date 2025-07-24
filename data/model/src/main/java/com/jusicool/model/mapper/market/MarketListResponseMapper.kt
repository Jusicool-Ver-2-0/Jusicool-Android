package com.jusicool.model.mapper.market

import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.model.market.MarketResponse

fun MarketResponse.toEntity() = Market(
    id = this.id,
    koreanName = this.koreanName,
    englishName = this.englishName ?: "",
    market = this.market,
    marketType = MarketType.valueOf(this.marketType),
)