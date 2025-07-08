package com.meister.community.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.meister.community.viewModel.uiState.WriteEditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WriteEditViewModel @Inject constructor(
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {
    val title: StateFlow<String> = saveStateHandle.getStateFlow("title", "")
    val content: StateFlow<String> = saveStateHandle.getStateFlow("content", "")

    val uiState: StateFlow<WriteEditUiState> = MutableStateFlow(WriteEditUiState.Loading).asStateFlow()

    fun editPost() {
        // TODO: 게시글 제출 로직 구현
    }

    fun onTitleChange(newTitle: String) {
        saveStateHandle["title"] = newTitle
    }

    fun onContentChange(newContent: String) {
        saveStateHandle["content"] = newContent
    }
}