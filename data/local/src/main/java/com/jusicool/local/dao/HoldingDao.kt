package com.jusicool.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jusicool.local.entity.HoldingEntity
import com.jusicool.local.entity.MarketEntity
import com.jusicool.local.relation.HoldingWithMarket
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldings(holdings: List<HoldingEntity>)

    @Query("SELECT * FROM holdings ORDER BY id ASC")
    suspend fun getAllHoldings(): List<HoldingEntity>

    @Query("DELETE FROM holdings WHERE id IN (:ids)")
    suspend fun deleteHoldingsByIds(ids: List<Int>)

    @Transaction
    @Query("SELECT * FROM holdings ORDER BY id ASC")
    fun observeAllHoldingsWithMarket(): Flow<List<HoldingWithMarket>>

    @Transaction
    suspend fun updateHoldings(
        newHoldings: List<HoldingEntity>,
        deleteIds: List<Int>,
        newMarkets: List<MarketEntity>,
        marketDao: MarketDao
    ) {
        if (deleteIds.isNotEmpty()) {
            deleteHoldingsByIds(deleteIds)
        }
        if (newMarkets.isNotEmpty()) {
            marketDao.insertMarkets(newMarkets)
        }
        insertHoldings(newHoldings)
    }
}

