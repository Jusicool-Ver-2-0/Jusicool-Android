package com.jusicool.network.datasource.community

import com.jusicool.model.community.WritePostRequest
import kotlinx.coroutines.flow.Flow

interface CommunityDataSource {
    fun postWrite(market: String, body: WritePostRequest): Flow<Unit>

}