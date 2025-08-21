package com.jusicool.usecase.community

import com.jusicool.entity.community.CommunityListModel
import com.jusicool.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommunityListUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    operator fun invoke(market: String): Flow<List<CommunityListModel>> =
        communityRepository.getList(market = market)
}