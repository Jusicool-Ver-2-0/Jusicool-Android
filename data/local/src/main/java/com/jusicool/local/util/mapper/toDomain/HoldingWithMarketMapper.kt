package com.jusicool.local.util.mapper.toDomain

import com.jusicool.entity.holding.HoldingModel
import com.jusicool.local.relation.HoldingWithMarket

fun HoldingWithMarket.toModel(): HoldingModel {
    return HoldingModel(
        id = holding.id,
        marketId = market.id,
        koreanName = market.koreanName,
        englishName = market.englishName,
        marketCode = market.market,
        marketType = market.type,
        quantity = holding.quantity,
        price = holding.price
    )
}
