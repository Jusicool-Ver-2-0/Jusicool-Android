package com.jusicool.network.datasource.community

import com.jusicool.model.community.WritePostRequest
import com.jusicool.network.api.CommunityApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommunityDataSourceImpl @Inject constructor(
    private val communityApi: CommunityApi
): CommunityDataSource {
    override fun postWrite(market: String, body: WritePostRequest): Flow<Unit> =
        performApiRequest { communityApi.postWrite(market = market, body = body) }

}