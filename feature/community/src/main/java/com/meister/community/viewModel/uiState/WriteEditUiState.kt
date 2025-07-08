package com.meister.community.viewModel.uiState

sealed interface WriteEditUiState {
    object Loading : WriteEditUiState
    object Success : WriteEditUiState
    data class Error(val message: String) : WriteEditUiState
}