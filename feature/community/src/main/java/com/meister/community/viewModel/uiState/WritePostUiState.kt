package com.meister.community.viewModel.uiState

sealed interface WritePostUiState {
    object Loading : WritePostUiState
    object Success : WritePostUiState
    data class Error(val message: String) : WritePostUiState
}