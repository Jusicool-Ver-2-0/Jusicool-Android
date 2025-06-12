package com.jusicool.entity.holding

data class HoldingModel(
    val id: Int,
    val marketId: Int,
    val koreanName: String,
    val englishName: String?,
    val marketCode: String,
    val marketType: String,
    val quantity: Int,
    val price: Int
)
