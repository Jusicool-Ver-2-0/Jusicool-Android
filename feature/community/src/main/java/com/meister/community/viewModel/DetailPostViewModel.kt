package com.meister.community.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.meister.community.viewModel.uiState.CommunityDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailPostViewModel @Inject constructor(
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {
    val commentState: StateFlow<String> = saveStateHandle.getStateFlow("commentState", "")
    val uiState: StateFlow<CommunityDetailUiState> = MutableStateFlow(CommunityDetailUiState()).asStateFlow()

    fun deletePost() {
        // TODO: 게시글 삭제 로직 구현
    }

    fun postComment() {
        // TODO: 댓글 작성 로직 구현
    }

    fun toggleLike() {
        // TODO: 좋아요 토글 로직 구현
    }

    fun onCommentStateChange(newComment: String) {
        saveStateHandle["commentState"] = newComment
    }
}