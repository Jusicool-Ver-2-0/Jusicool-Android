package com.jusicool.signup.viewModel.uiState

sealed interface VerificationCodeUiState {
    object Loading : VerificationCodeUiState
    object Success : VerificationCodeUiState
    data class Error(val message: String) : VerificationCodeUiState
    data class InvalidCode(val message: String) : VerificationCodeUiState
}