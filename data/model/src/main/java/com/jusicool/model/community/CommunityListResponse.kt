package com.jusicool.model.community

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityListResponse(
    val id: Int,
    val email: String,
    val market: String,
    val title: String,
    val content: String,
    @Json(name = "comment_count") val commentCount: Int,
    @Json(name = "like_count") val likeCount: Int,
    @Json(name = "is_liked") val isLiked: Boolean,
    @Json(name = "is_mine") val isMine: Boolean
)