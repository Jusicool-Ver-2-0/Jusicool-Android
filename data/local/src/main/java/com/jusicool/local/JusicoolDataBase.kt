package com.jusicool.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jusicool.local.dao.HoldingDao
import com.jusicool.local.dao.MarketDao
import com.jusicool.local.entity.HoldingEntity
import com.jusicool.local.entity.MarketEntity

@Database(
    entities = [
        HoldingEntity::class,
        MarketEntity::class,
    ], version = 0
)
abstract class JusicoolDataBase : RoomDatabase() {

    abstract fun holdingDao(): HoldingDao

    abstract fun marketDao(): MarketDao
}
