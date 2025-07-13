package com.jusicool.repository

import com.jusicool.model.community.WritePostRequest
import com.jusicool.network.datasource.community.CommunityDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityDataSource: CommunityDataSource
): CommunityRepository{
    override fun postWrite(market: String, body: WritePostRequest): Flow<Unit> {
        return communityDataSource.postWrite(market = market, body = body)
    }
}