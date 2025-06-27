package com.jusicool.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markets")
data class MarketEntity(
    @PrimaryKey val id: Int,
    val koreanName: String,
    val englishName: String,
    val market: String, // 예: KRW-BTC
    val type: String    // 예: STOCK, CRYPTO
)
