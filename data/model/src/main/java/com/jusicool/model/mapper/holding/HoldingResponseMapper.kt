package com.jusicool.model.mapper.holding

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.model.holding.HoldingResponse

fun HoldingResponse.toModel(): HoldingModel =
    HoldingModel(
        id = this.id,
        marketId = market.id,
        koreanName = market.koreanName,
        englishName = market.englishName,
        marketCode = market.market,
        marketType = market.marketType,
        quantity = this.quantity,
        price = this.price
    )
