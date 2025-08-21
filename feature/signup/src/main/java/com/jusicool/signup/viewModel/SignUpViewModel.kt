package com.jusicool.signup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.auth.SignUpModel
import com.jusicool.signup.viewModel.uiState.SignUpUiState
import com.jusicool.usecase.auth.SignUpRequestUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRequestUseCase: SignUpRequestUseCase
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)
    internal val signUpState = _signUpUiState.asStateFlow()

    private val _username= MutableStateFlow("")
    internal val username = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    internal val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    internal val password = _password.asStateFlow()

    internal fun onUsernameChange(value: String) {
        _username.value = value
    }

    internal fun onEmailChange(value: String) {
        _email.value = value
    }

    internal fun onPasswordChange(value: String) {
        _password.value = value
    }

    private fun signUp(body: SignUpModel) = viewModelScope.launch {
        _signUpUiState.value = SignUpUiState.Loading
        signUpRequestUseCase(body)
            .catch { e ->
                Logger.e("SignInViewModel", "로그인 실패: ${e.message}")
                _signUpUiState.value = SignUpUiState.Error(e.message ?: "Unknown error")
            }.collect {
                Logger.d("SignInViewModel", "로그인 성공")
                _signUpUiState.value = SignUpUiState.Success
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}