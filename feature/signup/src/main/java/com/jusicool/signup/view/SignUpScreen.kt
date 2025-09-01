// feature/signup/src/main/java/com/jusicool/signup/view/SignUpScreen.kt
package com.jusicool.signup.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.JusicoolTextField
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.signup.component.SchoolList
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon
import com.jusicool.design_system.icon.SearchIcon
import com.jusicool.entity.school.SchoolInfoModel
import com.jusicool.signup.viewModel.SignUpViewModel
import com.jusicool.signup.viewModel.uiState.SearchSchoolUiState
import com.jusicool.signup.viewModel.uiState.SignUpScreenState
import com.jusicool.signup.viewModel.uiState.VerificationCodeUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpRoute(
    modifier: Modifier = Modifier,
    navigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState { 4 }

    LaunchedEffect(state.verificationCodeUiState) {
        when (state.verificationCodeUiState) {
            is VerificationCodeUiState.Success -> pagerState.animateScrollToPage(2)
            is VerificationCodeUiState.InvalidCode -> viewModel.setVerificationCodeError(true)
            else -> Unit
        }
    }

    SignUpScreen(
        modifier = modifier,
        pagerState = pagerState,
        state = state,
        onSelectSchool = viewModel::onSelectSchool,
        onUsernameChange = viewModel::onUsernameChange,
        onEmailChange = viewModel::onEmailChange,
        onCodeChange = viewModel::onCodeChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onKeywordChange = viewModel::onKeywordChange,
        setEmailValidated = viewModel::setEmailValidated,
        setShowCodeInput = viewModel::setShowCodeInput,
        setPasswordError = viewModel::setPasswordError,
        setConfirmError = viewModel::setConfirmError,
        requestVerificationEmail = viewModel::requestVerificationEmail,
        requestVerificationCode = viewModel::requestVerificationCode,
        restartTimer = viewModel::startResendTimer,
        onSearchSchool = viewModel::searchSchool,
        requestSignUp = viewModel::signUp,
        navigateToSignIn = navigateToSignIn
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    state: SignUpScreenState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onKeywordChange: (String) -> Unit,
    setEmailValidated: (Boolean) -> Unit,
    setShowCodeInput: (Boolean) -> Unit,
    setPasswordError: (Boolean) -> Unit,
    setConfirmError: (Boolean) -> Unit,
    requestVerificationEmail: () -> Unit,
    requestVerificationCode: () -> Unit,
    restartTimer: () -> Unit,
    onSearchSchool: () -> Unit,
    onSelectSchool: (SchoolInfoModel) -> Unit,
    requestSignUp: () -> Unit,
    navigateToSignIn: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp, bottom = 32.dp)
        ) {
            LeftClarityArrowLineIcon(
                modifier = Modifier
                    .size(24.dp)
                    .JusicoolClickable {
                        if (pagerState.currentPage == 0) navigateToSignIn()
                        else coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(
                modifier = modifier.fillMaxSize(),
                state = pagerState,
                userScrollEnabled = false,
            ) { page ->
                when (page) {
                    0 -> {
                        Column {
                            Text(text = "이름을 입력해주세요", color = colors.black, style = typography.subTitle)
                            Spacer(modifier = Modifier.height(40.dp))
                            JusicoolTextField(
                                label = "이름",
                                textState = state.username,
                                onTextChange = onUsernameChange,
                                placeHolder = "이름을 입력해주세요"
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "다음",
                                state = if (state.username.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                                onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } }
                            )
                        }
                    }

                    1 -> {
                        val isEmailValid = state.email.contains("@")
                        Column {
                            Text(text = "이메일을 입력해주세요", color = colors.black, style = typography.subTitle)
                            Spacer(modifier = Modifier.height(40.dp))

                            if (!state.showCodeInput) {
                                JusicoolTextField(
                                    label = "이메일",
                                    textState = state.email,
                                    onTextChange = {
                                        onEmailChange(it)
                                        if (state.isEmailValidated) setEmailValidated(false)
                                    },
                                    placeHolder = "이메일 입력해주세요",
                                    isError = state.isEmailValidated && !isEmailValid,
                                    errorText = if (state.isEmailValidated && !isEmailValid) "이메일 형식을 다시 확인해주세요" else ""
                                )
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(text = "이메일", color = colors.black, style = typography.bodySmall)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(1.dp, colors.gray200, RoundedCornerShape(8.dp))
                                            .background(colors.gray100, RoundedCornerShape(8.dp))
                                            .padding(16.dp)
                                    ) {
                                        Text(text = state.email, color = colors.black, style = typography.bodySmall)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            modifier = Modifier.JusicoolClickable {
                                                setShowCodeInput(false)
                                                setEmailValidated(false)
                                                onCodeChange("")
                                            },
                                            text = if (state.showCodeInput) "이메일 수정하기" else "",
                                            color = colors.main,
                                            style = typography.label
                                        )
                                    }
                                }
                            }

                            if (state.showCodeInput) {
                                val timeText = String.format("%02d:%02d", state.remainingTime / 60, state.remainingTime % 60)
                                Spacer(modifier = Modifier.height(24.dp))
                                JusicoolTextField(
                                    label = "인증번호",
                                    textState = state.code,
                                    onTextChange = onCodeChange,
                                    placeHolder = "이메일로 전송된 인증번호 입력",
                                    helperText = if (!state.isVerificationCodeError) {
                                        if (state.isResendEnabled) "인증번호 재전송" else timeText
                                    } else "",
                                    isError = state.isVerificationCodeError,
                                    errorText = if (state.isVerificationCodeError) "인증번호가 올바르지 않습니다." else "",
                                    onHelperTextClick = {
                                        if (state.isResendEnabled) {
                                            requestVerificationEmail()
                                            onCodeChange("")
                                            restartTimer()
                                        }
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "다음",
                                state = when {
                                    !state.showCodeInput -> if (state.email.isNotBlank()) ButtonState.Enable else ButtonState.Disable
                                    else -> if (state.code.isNotBlank()) ButtonState.Enable else ButtonState.Disable
                                },
                                onClick = {
                                    if (!state.showCodeInput) {
                                        if (isEmailValid) {
                                            requestVerificationEmail()
                                            setShowCodeInput(true)
                                            restartTimer()
                                        } else {
                                            setEmailValidated(true)
                                        }
                                    } else {
                                        requestVerificationCode()
                                    }
                                }
                            )
                        }
                    }

                    2 -> {
                        Column {
                            Text(text = "비밀번호를 입력해주세요", color = colors.black, style = typography.subTitle)
                            Spacer(modifier = Modifier.height(40.dp))
                            JusicoolTextField(
                                label = "비밀번호",
                                textState = state.password,
                                onTextChange = {
                                    onPasswordChange(it)
                                    setPasswordError(false)
                                },
                                placeHolder = "비밀번호를 입력해주세요",
                                visualTransformation = PasswordVisualTransformation(),
                                helperText = if (state.isPasswordError) "" else "영문, 숫자, 특수문자 중 2개 이상의 조합으로 8글자 이상",
                                isError = state.isPasswordError,
                                errorText = if (state.isPasswordError) "비밀번호 형식을 확인해주세요" else ""
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            JusicoolTextField(
                                label = "비밀번호 재입력",
                                textState = state.confirmPassword,
                                onTextChange = {
                                    onConfirmPasswordChange(it)
                                    setConfirmError(false)
                                },
                                placeHolder = "비밀번호를 다시 입력해주세요",
                                visualTransformation = PasswordVisualTransformation(),
                                isError = state.isConfirmError,
                                errorText = if (state.isConfirmError) "비밀번호가 일치하지 않아요" else ""
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "다음",
                                state = if (state.password.isNotBlank() && state.confirmPassword.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                                onClick = {
                                    val passwordValid = state.password.length >= 8 &&
                                            listOf(
                                                Regex(".*[a-zA-Z].*").containsMatchIn(state.password),
                                                Regex(".*[0-9].*").containsMatchIn(state.password),
                                                Regex(".*[!@#\$%^&*(),.?\":{}|<>\\[\\]~`\\\\/;'+=-_].*").containsMatchIn(state.password)
                                            ).count { it } >= 2
                                    val confirmValid = state.password == state.confirmPassword && state.confirmPassword.isNotEmpty()
                                    if (!passwordValid) setPasswordError(true)
                                    if (!confirmValid) setConfirmError(true)
                                    if (passwordValid && confirmValid) {
                                        coroutineScope.launch { pagerState.animateScrollToPage(3) }
                                    }
                                }
                            )
                        }
                    }

                    3 -> {
                        Column {
                            Text(text = "현재 재학중인 학교 이름을 입력해주세요", color = colors.black, style = typography.subTitle)
                            Spacer(modifier = Modifier.height(40.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                JusicoolTextField(
                                    modifier = Modifier.weight(1f),
                                    label = "학교명",
                                    textState = state.keyword,
                                    onTextChange = onKeywordChange,
                                    placeHolder = "학교명을 입력해주세요",
                                )
                                Box(
                                    modifier = Modifier
                                        .border(1.dp, colors.main.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                        .padding(15.dp)
                                        .JusicoolClickable { onSearchSchool() }
                                ) {
                                    SearchIcon(modifier = Modifier.size(24.dp), tint = colors.main.copy(alpha = 0.5f))
                                }
                            }
                            when (val s = state.searchSchoolUiState) {
                                is SearchSchoolUiState.Success -> {
                                    SchoolList(
                                        modifier = Modifier.weight(1f),
                                        schools = s,
                                        selectedSchool = state.selectedSchool,
                                        onSelectSchool = onSelectSchool
                                    )
                                }
                                else -> {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "회원가입 완료",
                                state = if (state.selectedSchool != null) ButtonState.Enable else ButtonState.Disable,
                                onClick = {
                                    requestSignUp()
                                    navigateToSignIn()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        pagerState = rememberPagerState { 4 },
        state = SignUpScreenState(),
        onUsernameChange = {},
        onEmailChange = {},
        onCodeChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onKeywordChange = {},
        setEmailValidated = {},
        setShowCodeInput = {},
        setPasswordError = {},
        setConfirmError = {},
        requestVerificationEmail = {},
        requestVerificationCode = {},
        restartTimer = {},
        onSearchSchool = {},
        onSelectSchool = {},
        requestSignUp = {},
        navigateToSignIn = {}
    )
}
