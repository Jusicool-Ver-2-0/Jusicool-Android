package com.jusicool.signup.viewModel.uiState

sealed interface VerificationEmailUiState {
    object Loading : VerificationEmailUiState
    object Success : VerificationEmailUiState
    data class Error(val message: String) : VerificationEmailUiState
}