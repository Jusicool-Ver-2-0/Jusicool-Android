package com.jusicool.repository

import com.jusicool.entity.community.CommunityListModel
import com.jusicool.model.community.CommunityListResponse
import com.jusicool.model.community.WritePostRequest
import kotlinx.coroutines.flow.Flow

interface CommunityRepository {
    fun postWrite(market: String, body: WritePostRequest): Flow<Unit>

    fun getList(market: String): Flow<List<CommunityListModel>>
}