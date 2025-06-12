package com.jusicool.signin.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.auth.SignInModel
import com.jusicool.model.auth.SignInRequest
import com.jusicool.usecase.auth.SignInRequestUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRequestUseCase: SignInRequestUseCase
) : ViewModel() {

    private val _signInUiState = MutableStateFlow<SignInUiState>(SignInUiState.Loading)
    internal val signInUiState = _signInUiState.asStateFlow()

    private val _email = MutableStateFlow("")
    internal val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    internal val password = _password.asStateFlow()

    private val _isEmailError = MutableStateFlow(false)
    internal val isEmailError = _isEmailError.asStateFlow()

    private val _isPasswordError = MutableStateFlow(false)
    internal val isPasswordError = _isPasswordError.asStateFlow()

    internal fun onEmailChange(value: String) {
        _email.value = value
        _isEmailError.value = false
    }

    internal fun onPasswordChange(value: String) {
        _password.value = value
        _isPasswordError.value = false
    }

    internal fun onSignInClick() {
        val emailValue = email.value
        val passwordValue = password.value

        val isEmailValid = isValidEmail(emailValue)
        val isPasswordValid = passwordValue.isNotEmpty()

        _isEmailError.value = !isEmailValid
        _isPasswordError.value = !isPasswordValid

        if (isEmailValid && isPasswordValid) {
            signIn(SignInModel(email = emailValue, password = passwordValue))
        }
    }

    private fun signIn(body: SignInModel) = viewModelScope.launch {
        _signInUiState.value = SignInUiState.Loading
        signInRequestUseCase(body)
            .onSuccess {
                it.catch { e ->
                    Logger.e("SignInViewModel", "로그인 실패: ${e.message}")
                    _signInUiState.value = SignInUiState.Error(e.message ?: "Unknown error")
                }.collect {
                    Logger.d("SignInViewModel", "로그인 성공")
                    _signInUiState.value = SignInUiState.Success
                }
            }
            .onFailure {
                Logger.e("SignInViewModel", "로그인 실패: ${it.message}")
                _signInUiState.value = SignInUiState.Error(it.message ?: "Unknown error")
            }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}
