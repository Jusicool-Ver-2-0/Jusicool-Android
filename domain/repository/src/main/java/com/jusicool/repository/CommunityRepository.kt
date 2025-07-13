package com.jusicool.repository

import com.jusicool.model.community.WritePostRequest
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun postWrite(market: String, body: WritePostRequest): Flow<Unit>

}