package com.jusicool.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.jusicool.local.entity.MarketEntity
import com.jusicool.local.entity.HoldingEntity

data class HoldingWithMarket(
    @Embedded val holding: HoldingEntity,
    @Relation(
        parentColumn = "marketId",
        entityColumn = "id"
    )
    val market: MarketEntity
)
