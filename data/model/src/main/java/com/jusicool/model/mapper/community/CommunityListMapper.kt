package com.jusicool.model.mapper.community

import com.jusicool.entity.community.CommunityListModel
import com.jusicool.model.community.CommunityListResponse

fun CommunityListResponse.toModel(): CommunityListModel =
    CommunityListModel(
        id = this.id,
        email = this.email,
        market = this.market,
        title = this.title,
        content = this.content,
        username = this.username,
        commentCount = this.commentCount,
        likeCount = this.likeCount,
        isLiked = this.isLiked,
        isMine = this.isMine,
        createdAt = this.createdAt
    )