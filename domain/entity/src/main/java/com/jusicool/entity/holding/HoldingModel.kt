package com.jusicool.entity.holding

import com.jusicool.entity.market.Market

data class HoldingModel(
    val id: Int,
    val market: Market,
    val quantity: Int,
    val price: Int
)
