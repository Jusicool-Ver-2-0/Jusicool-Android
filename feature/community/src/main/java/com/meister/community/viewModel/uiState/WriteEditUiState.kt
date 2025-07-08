package com.meister.community.viewModel.uiState

sealed interface WriteEditUiState {
    object Loading : WritePostUiState
    object Success : WritePostUiState
    data class Error(val message: String) : WritePostUiState
}