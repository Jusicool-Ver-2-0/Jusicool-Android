package com.jusicool.signup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusicool.entity.auth.SignUpModel
import com.jusicool.entity.auth.VerificationCodeModel
import com.jusicool.entity.auth.VerificationEmailModel
import com.jusicool.entity.school.SchoolInfoModel
import com.jusicool.signup.viewModel.uiState.SearchSchoolUiState
import com.jusicool.signup.viewModel.uiState.SignUpUiState
import com.jusicool.signup.viewModel.uiState.VerificationCodeUiState
import com.jusicool.signup.viewModel.uiState.VerificationEmailUiState
import com.jusicool.usecase.auth.VerificationEmailUseCase
import com.jusicool.usecase.auth.SignUpRequestUseCase
import com.jusicool.usecase.auth.VerificationCodeUseCase
import com.jusicool.usecase.school.SearchSchoolUseCase
import com.jusicool.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRequestUseCase: SignUpRequestUseCase,
    private val verificationEmailUseCase: VerificationEmailUseCase,
    private val verificationCodeUseCase: VerificationCodeUseCase,
    private val searchSchoolUseCase: SearchSchoolUseCase
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading)

    private val _verificationEmailUiState = MutableStateFlow<VerificationEmailUiState>(VerificationEmailUiState.Loading)

    private val _verificationCodeUiState = MutableStateFlow<VerificationCodeUiState>(VerificationCodeUiState.Loading)
    internal val verificationCodeUiState = _verificationCodeUiState.asStateFlow()

    private val _searchSchoolUiState = MutableStateFlow<SearchSchoolUiState>(SearchSchoolUiState.Loading)
    internal val searchSchoolUiState = _searchSchoolUiState.asStateFlow()

    private val _username= MutableStateFlow("")
    internal val username = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    internal val email = _email.asStateFlow()

    private val _code = MutableStateFlow("")
    internal val code = _code.asStateFlow()

    private val _password = MutableStateFlow("")
    internal val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    internal val confirmPassword = _confirmPassword.asStateFlow()

    private val _keyword = MutableStateFlow("")
    internal val keyword = _keyword.asStateFlow()

    private val _isEmailValidated = MutableStateFlow(false)
    internal val isEmailValidated = _isEmailValidated.asStateFlow()

    private val _showCodeInput = MutableStateFlow(false)
    internal val showCodeInput = _showCodeInput.asStateFlow()

    private val _isPasswordError = MutableStateFlow(false)
    internal val isPasswordError = _isPasswordError.asStateFlow()

    private val _isConfirmError = MutableStateFlow(false)
    internal val isConfirmError = _isConfirmError.asStateFlow()

    private val _isVerificationCodeError = MutableStateFlow(false)
    internal val isVerificationCodeError = _isVerificationCodeError.asStateFlow()

    private val _selectedSchool = MutableStateFlow<SchoolInfoModel?>(null)
    internal val selectedSchool = _selectedSchool.asStateFlow()

    private val _remainingTime = MutableStateFlow(0)
    val remainingTime = _remainingTime.asStateFlow()

    internal fun onUsernameChange(value: String) {
        _username.value = value
    }

    internal fun onEmailChange(value: String) {
        _email.value = value
        _isEmailValidated.value = false
    }

    internal fun onCodeChange(value: String) {
        _code.value = value
        _isVerificationCodeError.value = false
    }

    internal fun onPasswordChange(value: String) {
        _password.value = value
    }

    internal fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
    }

    internal fun onKeywordChange(value: String) {
        _keyword.value = value
    }

    internal fun setEmailValidated(value: Boolean) {
        _isEmailValidated.value = value
    }

    internal fun setShowCodeInput(value: Boolean) {
        _showCodeInput.value = value
    }

    internal fun setPasswordError(value: Boolean) {
        _isPasswordError.value = value
    }

    internal fun setConfirmError(value: Boolean) {
        _isConfirmError.value = value
    }

    internal fun setVerificationCodeError(value: Boolean) {
        _isVerificationCodeError.value = value
    }

    internal fun onSelectSchool(school: SchoolInfoModel) {
        _selectedSchool.value = school
    }

    private val _isResendEnabled = MutableStateFlow(true)
    val isResendEnabled = _isResendEnabled.asStateFlow()

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
                if (e is HttpException && e.code() == 400) {
                    Logger.d("SignUPViewModel", "잘못된 인증번호")
                    _verificationCodeUiState.value = VerificationCodeUiState.InvalidCode("인증번호가 올바르지 않습니다.")
                } else {
                    Logger.d("SignUPViewModel", "인증 코드 검증 실패: ${e.message}")
                    _verificationCodeUiState.value = VerificationCodeUiState.Error(e.message ?: "Unknown error")
                }
            }.collect {
                Logger.d("SignUpViewModel", "인증코드 보내기 성공")
                _verificationCodeUiState.value = VerificationCodeUiState.Success
            }
    }

    internal fun signUp(body: SignUpModel) = viewModelScope.launch {
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

    internal fun requestVerificationEmail() {
        val emailValue = email.value
        if (emailValue.isNotBlank()) {
            verificationEmail(
                VerificationEmailModel(email = emailValue)
            )
        }
    }

    internal fun requestVerificationCode() {
        val emailValue = email.value
        val codeValue = code.value
        if (emailValue.isNotBlank() && codeValue.isNotBlank()) {
            verificationCode(
                VerificationCodeModel(email = emailValue, code = codeValue.toInt())
            )
        }
    }

    internal fun searchSchool() = viewModelScope.launch {
        _searchSchoolUiState.value = SearchSchoolUiState.Loading
        searchSchoolUseCase(keyword = keyword.value)
            .catch { e ->
                Logger.d("SignUpViewModel", "학교 검색 실패: ${e.message}")
                _searchSchoolUiState.value = SearchSchoolUiState.Error(e.message?: "Unknown error")
            }.collect { schools ->
                Logger.d("SignUpViewModel", "학교 검색 성공")
                _searchSchoolUiState.value = SearchSchoolUiState.Success(schools)
            }
    }

    internal fun startResendTimer() {
        viewModelScope.launch {
            _isResendEnabled.value = false
            _remainingTime.value = 60

            while (_remainingTime.value > 0) {
                delay(1000)
                _remainingTime.value = _remainingTime.value - 1
            }

            _isResendEnabled.value = true
        }
    }
}
