package com.jusicool.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "holdings",
    foreignKeys = [
        ForeignKey(
            entity = MarketEntity::class,
            parentColumns = ["id"],
            childColumns = ["marketId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["marketId"])]
)
data class HoldingEntity(
    @PrimaryKey val id: Int, // 보유 ID
    val marketId: Int,       // MarketEntity 참조
    val quantity: Int,
    val price: Int
)
