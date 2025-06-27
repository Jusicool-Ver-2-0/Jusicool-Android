package com.jusicool.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jusicool.local.entity.HoldingEntity
import com.jusicool.local.relation.HoldingWithMarket
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolding(holding: HoldingEntity)

    @Query("SELECT * FROM holdings")
    fun observeAllHoldings(): Flow<List<HoldingEntity>>

    @Query("SELECT * FROM holdings WHERE id = :holdingId")
    fun observeHoldingById(holdingId: Int): Flow<HoldingEntity?>

    @Delete
    suspend fun deleteHolding(holding: HoldingEntity)

    @Transaction
    @Query("SELECT * FROM holdings")
    fun observeAllHoldingsWithMarket(): Flow<List<HoldingWithMarket>>

    @Transaction
    @Query("SELECT * FROM holdings WHERE id = :holdingId")
    fun observeHoldingWithMarketById(holdingId: Int): Flow<HoldingWithMarket>?
}
