package com.meister.community.viewModel.uiState

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class CommunityDetailUiState(
    val isLoading: Boolean = false,
    val communityId: String = "",     // 커뮤니티 ID
    val postId: String = "",          // 게시글 ID
    val title: String = "",           // 게시글 제목
    val content: String = "",         // 게시글 내용
    val isLiked: Boolean = false,     // 좋아요 여부
    val likeCount: Int = 0,           // 좋아요 개수
    val comments: PersistentList<String> = persistentListOf(), // 댓글 리스트
    val errorMessage: String? = null,
)
