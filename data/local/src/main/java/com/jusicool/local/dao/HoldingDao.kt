package com.jusicool.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jusicool.local.entity.HoldingEntity
import com.jusicool.local.relation.HoldingWithMarket

@Dao
interface HoldingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolding(holding: HoldingEntity)

    @Query("SELECT * FROM holdings")
    suspend fun getAllHoldings(): List<HoldingEntity>

    @Query("SELECT * FROM holdings WHERE id = :holdingId")
    suspend fun getHoldingById(holdingId: Int): HoldingEntity?

    @Delete
    suspend fun deleteHolding(holding: HoldingEntity)

    @Transaction
    @Query("SELECT * FROM holdings")
    suspend fun getAllHoldingsWithMarket(): List<HoldingWithMarket>

    @Transaction
    @Query("SELECT * FROM holdings WHERE id = :holdingId")
    suspend fun getHoldingWithMarketById(holdingId: Int): HoldingWithMarket?
}
