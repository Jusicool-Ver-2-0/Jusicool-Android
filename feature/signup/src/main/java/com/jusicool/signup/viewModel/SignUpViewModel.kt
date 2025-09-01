// feature/signup/src/main/java/com/jusicool/signup/viewModel/SignUpViewModel.kt
package com.jusicool.signup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.auth.SignUpModel
import com.jusicool.entity.auth.VerificationCodeModel
import com.jusicool.entity.auth.VerificationEmailModel
import com.jusicool.entity.school.SchoolInfoModel
import com.jusicool.signup.viewModel.uiState.*
import com.jusicool.usecase.auth.SignUpRequestUseCase
import com.jusicool.usecase.auth.VerificationCodeUseCase
import com.jusicool.usecase.auth.VerificationEmailUseCase
import com.jusicool.usecase.school.SearchSchoolUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRequestUseCase: SignUpRequestUseCase,
    private val verificationEmailUseCase: VerificationEmailUseCase,
    private val verificationCodeUseCase: VerificationCodeUseCase,
    private val searchSchoolUseCase: SearchSchoolUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpScreenState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(v: String) = setState { copy(username = v) }
    fun onEmailChange(v: String) = setState { copy(email = v, isEmailValidated = false) }
    fun onCodeChange(v: String) = setState { copy(code = v, isVerificationCodeError = false) }
    fun onPasswordChange(v: String) = setState { copy(password = v) }
    fun onConfirmPasswordChange(v: String) = setState { copy(confirmPassword = v) }
    fun onKeywordChange(v: String) = setState { copy(keyword = v) }
    fun onSelectSchool(s: SchoolInfoModel) = setState { copy(selectedSchool = s) }

    fun setEmailValidated(v: Boolean) = setState { copy(isEmailValidated = v) }
    fun setShowCodeInput(v: Boolean) = setState { copy(showCodeInput = v) }
    fun setPasswordError(v: Boolean) = setState { copy(isPasswordError = v) }
    fun setConfirmError(v: Boolean) = setState { copy(isConfirmError = v) }
    fun setVerificationCodeError(v: Boolean) = setState { copy(isVerificationCodeError = v) }

    fun requestVerificationEmail() = viewModelScope.launch {
        val email = uiState.value.email
        if (email.isBlank()) return@launch
        setState { copy(verificationEmailUiState = VerificationEmailUiState.Loading) }
        verificationEmailUseCase(VerificationEmailModel(email))
            .catch { e ->
                Logger.e("SignUPViewModel", "인증 메일 실패: ${e.message}")
                setState { copy(verificationEmailUiState = VerificationEmailUiState.Error(e.message ?: "Unknown error")) }
            }
            .collect {
                Logger.d("SignUPViewModel", "인증 메일 성공")
                setState { copy(verificationEmailUiState = VerificationEmailUiState.Success) }
                startResendTimer()
            }
    }

    fun requestVerificationCode() = viewModelScope.launch {
        val email = uiState.value.email
        val codeInt = uiState.value.code.toIntOrNull()
        if (email.isBlank() || codeInt == null) {
            setState { copy(verificationCodeUiState = VerificationCodeUiState.InvalidCode("숫자 6자리를 입력하세요.")) }
            return@launch
        }
        setState { copy(verificationCodeUiState = VerificationCodeUiState.Loading) }
        verificationCodeUseCase(VerificationCodeModel(email = email, code = codeInt))
            .catch { e ->
                if (e is HttpException && e.code() == 400) {
                    Logger.d("SignUPViewModel", "잘못된 인증번호")
                    setState { copy(verificationCodeUiState = VerificationCodeUiState.InvalidCode("인증번호가 올바르지 않습니다.")) }
                } else {
                    Logger.d("SignUPViewModel", "인증 코드 검증 실패: ${e.message}")
                    setState { copy(verificationCodeUiState = VerificationCodeUiState.Error(e.message ?: "Unknown error")) }
                }
            }
            .collect {
                Logger.d("SignUpViewModel", "인증코드 성공")
                setState { copy(verificationCodeUiState = VerificationCodeUiState.Success) }
            }
    }

    fun signUp() = viewModelScope.launch {
        val s = uiState.value
        setState { copy(signUpUiState = SignUpUiState.Loading) }
        signUpRequestUseCase(
            SignUpModel(
                username = s.username,
                email = s.email,
                password = s.password,
                school = s.selectedSchool?.name ?: ""
            )
        )
            .catch { e ->
                Logger.e("SignUPViewModel", "회원가입 실패: ${e.message}")
                setState { copy(signUpUiState = SignUpUiState.Error(e.message ?: "Unknown error")) }
            }
            .collect {
                Logger.d("SignUPViewModel", "회원가입 성공")
                setState { copy(signUpUiState = SignUpUiState.Success) }
            }
    }

    fun searchSchool() = viewModelScope.launch {
        val q = uiState.value.keyword
        setState { copy(searchSchoolUiState = SearchSchoolUiState.Loading) }
        searchSchoolUseCase(keyword = q)
            .catch { e ->
                Logger.d("SignUpViewModel", "학교 검색 실패: ${e.message}")
                setState { copy(searchSchoolUiState = SearchSchoolUiState.Error(e.message ?: "Unknown error")) }
            }
            .collect { schools ->
                Logger.d("SignUpViewModel", "학교 검색 성공")
                setState { copy(searchSchoolUiState = SearchSchoolUiState.Success(schools)) }
            }
    }

    fun startResendTimer() = viewModelScope.launch {
        setState { copy(isResendEnabled = false, remainingTime = 60) }
        while (uiState.value.remainingTime > 0) {
            delay(1000)
            setState { copy(remainingTime = remainingTime - 1) }
        }
        setState { copy(isResendEnabled = true) }
    }

    private inline fun setState(reducer: SignUpScreenState.() -> SignUpScreenState) {
        _uiState.update { it.reducer() }
    }
}
