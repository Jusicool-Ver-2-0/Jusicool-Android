package com.jusicool.signup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.auth.SignUpModel
import com.jusicool.entity.auth.VerificationCodeModel
import com.jusicool.entity.auth.VerificationEmailModel
import com.jusicool.signup.viewModel.uiState.SignUpUiState
import com.jusicool.signup.viewModel.uiState.VerificationCodeUiState
import com.jusicool.signup.viewModel.uiState.VerificationEmailUiState
import com.jusicool.usecase.auth.VerificationEmailUseCase
import com.jusicool.usecase.auth.SignUpRequestUseCase
import com.jusicool.usecase.auth.VerificationCodeUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRequestUseCase: SignUpRequestUseCase,
    private val verificationEmailUseCase: VerificationEmailUseCase,
    private val verificationCodeUseCase: VerificationCodeUseCase
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)
    internal val signUpState = _signUpUiState.asStateFlow()

    private val _verificationEmailUiState = MutableStateFlow<VerificationEmailUiState>(VerificationEmailUiState.Loading)
    internal val verificationEmailUiState = _verificationEmailUiState.asStateFlow()

    private val _verificationCodeUiState = MutableStateFlow<VerificationCodeUiState>(VerificationCodeUiState.Loading)
    internal val verificationCodeUiState = _verificationCodeUiState.asStateFlow()

    private val _username= MutableStateFlow("")
    internal val username = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    internal val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    internal val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    internal val confirmPassword = _confirmPassword.asStateFlow()

    internal fun onUsernameChange(value: String) {
        _username.value = value
    }

    internal fun onEmailChange(value: String) {
        _email.value = value
    }

    internal fun onPasswordChange(value: String) {
        _password.value = value
    }

    internal fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
    }

    private fun signUp(body: SignUpModel) = viewModelScope.launch {
        _signUpUiState.value = SignUpUiState.Loading
        signUpRequestUseCase(body)
            .catch { e ->
                Logger.e("SignUPViewModel", "로그인 실패: ${e.message}")
                _signUpUiState.value = SignUpUiState.Error(e.message ?: "Unknown error")
            }.collect {
                Logger.d("SignUPViewModel", "로그인 성공")
                _signUpUiState.value = SignUpUiState.Success
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    private fun verificationEmail(body: VerificationEmailModel) = viewModelScope.launch {
        _verificationEmailUiState.value = VerificationEmailUiState.Loading
        verificationEmailUseCase(body)
            .catch { e ->
                Logger.e("SignUPViewModel", "인증 메일 보내기 실패: ${e.message}")
                _verificationEmailUiState.value = VerificationEmailUiState.Error(e.message ?: "Unknown error")
            }.collect {
                Logger.d("SignUPViewModel", "인증 메일 보내기 성공")
                _verificationEmailUiState.value = VerificationEmailUiState.Success
            }
    }

    private fun verificationCode(body: VerificationCodeModel) = viewModelScope.launch {
        _verificationCodeUiState.value = VerificationCodeUiState.Loading
        verificationCodeUseCase(body)
            .catch { e ->
                Logger.d("SignUPViewModel", "인증 코드 보내기 실패: ${e.message}")
                _verificationCodeUiState.value = VerificationCodeUiState.Error(e.message ?: "Unknown error")
            }.collect {
                Logger.d("SignUpViewModel", "인증코드 보내기 성공")
                _verificationCodeUiState.value = VerificationCodeUiState.Success
            }
    }
}