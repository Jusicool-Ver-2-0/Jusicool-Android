package com.jusicool.entity.community

data class CommunityListModel (
    val id: Int,
    val email: String,
    val market: String,
    val title: String,
    val content: String,
    val commentCount: Int,
    val likeCount: Int,
    val isLiked: Boolean,
    val isMine: Boolean
)