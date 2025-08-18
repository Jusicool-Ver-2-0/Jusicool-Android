package com.jusicool.usecase.community

import com.jusicool.model.community.WritePostRequest
import com.jusicool.repository.CommunityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostWriteUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) {
    operator fun invoke(market: String, body: WritePostRequest): Flow<Unit> =
        communityRepository.postWrite(market = market, body = body)
}