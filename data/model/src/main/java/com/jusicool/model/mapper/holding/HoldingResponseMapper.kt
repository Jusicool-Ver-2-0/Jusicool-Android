package com.jusicool.model.mapper.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.model.holding.HoldingResponse
import com.jusicool.model.holding.MarketDto

fun HoldingResponse.toModel(): HoldingModel =
    HoldingModel(
        id = this.id,
        market = this.marketDto.toEntity(),
        quantity = this.quantity,
        price = this.price
    )

fun MarketDto.toEntity(): Market =
    Market(
        id = this.id,
        koreanName = this.koreanName,
        englishName = this.englishName ?: "",
        market = this.market,
        marketType = MarketType.valueOf(this.marketType)
    )