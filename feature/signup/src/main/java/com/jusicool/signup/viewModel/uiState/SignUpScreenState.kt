package com.jusicool.signup.viewModel.uiState

import com.jusicool.entity.school.SchoolInfoModel

data class SignUpScreenState(
    val username: String = "",
    val email: String = "",
    val code: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val keyword: String = "",
    val selectedSchool: SchoolInfoModel? = null,

    val isEmailValidated: Boolean = false,
    val showCodeInput: Boolean = false,
    val isPasswordError: Boolean = false,
    val isConfirmError: Boolean = false,
    val isVerificationCodeError: Boolean = false,

    val remainingTime: Int = 0,
    val isResendEnabled: Boolean = true,

    val verificationEmailUiState: VerificationEmailUiState = VerificationEmailUiState.Loading,
    val verificationCodeUiState: VerificationCodeUiState = VerificationCodeUiState.Loading,
    val signUpUiState: SignUpUiState = SignUpUiState.Loading,
    val searchSchoolUiState: SearchSchoolUiState = SearchSchoolUiState.Loading
)