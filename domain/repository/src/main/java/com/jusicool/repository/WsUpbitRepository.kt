package com.jusicool.repository

import com.jusicool.entity.price.AssetsCurrentPrice
import kotlinx.coroutines.flow.Flow

interface WsUpbitRepository {
    fun observeTicker(markets: List<String>): Flow<List<AssetsCurrentPrice>>
}
