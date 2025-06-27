package com.jusicool.local.util.mapper.toLocal

import com.jusicool.local.entity.HoldingEntity
import com.jusicool.local.entity.MarketEntity
import com.jusicool.model.holding.HoldingResponse

fun HoldingResponse.toEntities(): Pair<HoldingEntity, MarketEntity> {
    val holdingEntity = HoldingEntity(
        id = this.id,
        marketId = this.market.id,
        quantity = this.quantity,
        price = this.price
    )

    val marketEntity = MarketEntity(
        id = this.market.id,
        koreanName = this.market.koreanName,
        englishName = this.market.englishName ?: "",
        market = this.market.market,
        type = this.market.marketType
    )

    return Pair(holdingEntity, marketEntity)
}
