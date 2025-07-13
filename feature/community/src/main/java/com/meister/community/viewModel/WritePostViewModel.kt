package com.meister.community.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.model.community.WritePostRequest
import com.jusicool.usecase.community.PostWriteUseCase
import com.meister.community.viewModel.uiState.WritePostUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jusicool.utils.Logger

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val postWriteUseCase: PostWriteUseCase,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {
    val title: StateFlow<String> = saveStateHandle.getStateFlow("title", "")
    val content: StateFlow<String> = saveStateHandle.getStateFlow("content", "")

    private val _writePostUiState = MutableStateFlow<WritePostUiState>(WritePostUiState.Loading)
    internal val writePostUiState = _writePostUiState.asStateFlow()

    fun submitPost(market: String) {
        writePost(market = market, WritePostRequest(title = title.value, content = content.value))
    }

    private fun writePost(market: String ,body: WritePostRequest)  = viewModelScope.launch {
        _writePostUiState.value = WritePostUiState.Loading
        postWriteUseCase(market = market, body = body)
            .onSuccess {
                it.catch { e ->
                    Logger.e("WritePostViewModel", "글쓰기 실패: ${e.message}")
                    _writePostUiState.value = WritePostUiState.Error(e.message ?: "Unknown error")
                }.collect {
                    Logger.d("WritePostViewModel", "글쓰기 성공")
                    _writePostUiState.value = WritePostUiState.Success
                }
            }
            .onFailure {
                Logger.e("WritePostViewModel", "글쓰기 실패: ${it.message}")
                _writePostUiState.value = WritePostUiState.Error(it.message ?: "Unknown error")
            }
    }

    fun onTitleChange(newTitle: String) {
        saveStateHandle["title"] = newTitle
    }

    fun onContentChange(newContent: String) {
        saveStateHandle["content"] = newContent
    }
}