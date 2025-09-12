package com.jusicool.repository

import com.jusicool.entity.community.CommunityListModel
import com.jusicool.model.community.WritePostRequest
import com.jusicool.model.mapper.community.toModel
import com.jusicool.model.mapper.holding.toModel
import com.jusicool.network.datasource.community.CommunityDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommunityRepositoryImpl @Inject constructor(
    private val communityDataSource: CommunityDataSource
): CommunityRepository{
    override fun postWrite(market: String, body: WritePostRequest): Flow<Unit> {
        return communityDataSource.postWrite(market = market, body = body)
    }

    override fun getList(market: String): Flow<List<CommunityListModel>> {
        return communityDataSource.getList(market = market).map { list -> list.map { it.toModel() } }
    }
}