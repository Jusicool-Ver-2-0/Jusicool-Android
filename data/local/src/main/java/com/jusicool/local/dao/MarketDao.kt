package com.jusicool.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jusicool.local.entity.MarketEntity

@Dao
interface MarketDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMarket(market: MarketEntity)

    @Query("SELECT * FROM markets")
    suspend fun getAllMarkets(): List<MarketEntity>

    @Query("SELECT * FROM markets WHERE id = :marketId")
    suspend fun getMarketById(marketId: Int): MarketEntity?

    @Delete
    suspend fun deleteMarket(market: MarketEntity)
}
