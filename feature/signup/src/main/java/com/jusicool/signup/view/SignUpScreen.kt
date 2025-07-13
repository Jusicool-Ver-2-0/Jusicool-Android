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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.JusicoolTextField
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.signup.component.School
import com.jusicool.signup.component.SchoolList
import com.jusicool.signup.component.SchoolListItem
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon
import com.jusicool.design_system.icon.SearchIcon
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { 4 }
    val coroutineScope = rememberCoroutineScope()

    val (nameTextState, nameOnTextChange) = remember { mutableStateOf("") }
    val (emailTextState, emailOnTextChange) = remember { mutableStateOf("") }
    val (codeTextState, codeOnTextChange) = remember { mutableStateOf("") }
    val (passwordTextState, passwordOnTextChange) = remember { mutableStateOf("") }
    val (confirmTextState, confirmOnTextChange) = remember { mutableStateOf("") }
    val (schoolTextState, schoolOnTextChange) = remember { mutableStateOf("") }

    val (isEmailValidated, setEmailValidated) = remember { mutableStateOf(false) }
    val (showCodeInput, setShowCodeInput) = remember { mutableStateOf(false) }
    val (isPasswordError, setPasswordError) = remember { mutableStateOf(false) }
    val (isConfirmError, setConfirmError) = remember { mutableStateOf(false) }

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
                        if (pagerState.currentPage == 0)
                        /*TODO()*/
                        else
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
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
                            Text(
                                text = "이름을 입력해주세요",
                                color = colors.black,
                                style = typography.subTitle
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            JusicoolTextField(
                                label = "이름",
                                textState = nameTextState,
                                onTextChange = nameOnTextChange,
                                placeHolder = "이름을 입력해주세요"
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "다음",
                                state = if (nameTextState.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(3)
                                    }
                                }
                            )
                        }
                    }

                    1 -> {
                        val isEmailValid = emailTextState.contains("@")

                        Column {
                            Text(
                                text = "이메일을 입력해주세요",
                                color = colors.black,
                                style = typography.subTitle
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            if (!showCodeInput) {
                                JusicoolTextField(
                                    label = "이메일",
                                    textState = emailTextState,
                                    onTextChange = {
                                        emailOnTextChange(it)
                                        if (isEmailValidated) setEmailValidated(false)
                                    },
                                    placeHolder = "이메일 입력해주세요",
                                    isError = isEmailValidated && !isEmailValid,
                                    errorText = if (isEmailValidated && !isEmailValid) "이메일 형식을 다시 확인해주세요" else ""
                                )
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "이메일",
                                        color = colors.black,
                                        style = typography.bodySmall
                                    )

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(width = 1.dp, color = colors.gray200, shape = RoundedCornerShape(size = 8.dp))
                                            .background(color = colors.gray100, shape = RoundedCornerShape(size = 8.dp))
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = emailTextState,
                                            color = colors.black,
                                            style = typography.bodySmall
                                        )
                                    }
                                }
                            }

                            if (showCodeInput) {
                                Spacer(modifier = Modifier.height(24.dp))

                                JusicoolTextField(
                                    label = "인증번호",
                                    textState = codeTextState,
                                    onTextChange = codeOnTextChange,
                                    placeHolder = "이메일로 전송된 인증번호 입력",
                                    helperText = "이메일 수정하기",
                                    onHelperTextClick = {
                                        setShowCodeInput(false)
                                        setEmailValidated(false)
                                        codeOnTextChange("")
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "다음",
                                state = when {
                                    !showCodeInput -> if (emailTextState.isNotBlank())  ButtonState.Enable else ButtonState.Disable
                                    else -> if (codeTextState.isNotBlank()) ButtonState.Enable else ButtonState.Disable
                                },
                                onClick = {
                                    if (!showCodeInput) {
                                        if (isEmailValid) {
                                            setShowCodeInput(true)
                                        } else {
                                            setEmailValidated(true)
                                        }
                                    } else {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(2)
                                            // TODO: 실제 이메일 전송 API 호출
                                        }
                                    }
                                }
                            )
                        }
                    }

                    2 -> {
                        Column {
                            Text(
                                text = "비밀번호를 입력해주세요",
                                color = colors.black,
                                style = typography.subTitle
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            JusicoolTextField(
                                label = "비밀번호",
                                textState = passwordTextState,
                                onTextChange = {
                                    passwordOnTextChange(it)
                                    setPasswordError(false)
                                },
                                placeHolder = "비밀번호를 입력해주세요",
                                visualTransformation = PasswordVisualTransformation(),
                                helperText = if (isPasswordError) "" else "영문, 숫자, 특수문자 중 2개 이상의 조합으로 8글자 이상",
                                isError = isPasswordError,
                                errorText = if (isPasswordError) "비밀번호 형식을 확인해주세요" else ""
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            JusicoolTextField(
                                label = "비밀번호 재입력",
                                textState = confirmTextState,
                                onTextChange = {
                                    confirmOnTextChange(it)
                                    setConfirmError(false)
                                },
                                placeHolder = "비밀번호를 다시 입력해주세요",
                                visualTransformation = PasswordVisualTransformation(),
                                isError = isConfirmError,
                                errorText = if (isConfirmError) "비밀번호가 일치하지 않아요" else ""
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "다음",
                                state = if (passwordTextState.isNotBlank() && confirmTextState.isNotBlank()) ButtonState.Enable else ButtonState.Disable,
                                onClick = {
                                    val passwordValid = passwordTextState.length >= 8 &&
                                            listOf(
                                                Regex(".*[a-zA-Z].*").containsMatchIn(passwordTextState),
                                                Regex(".*[0-9].*").containsMatchIn(passwordTextState),
                                                Regex(".*[!@#\$%^&*(),.?\":{}|<>\\[\\]~`\\\\/;'+=-_].*").containsMatchIn(passwordTextState)
                                            ).count { it } >= 2

                                    val confirmValid = passwordTextState == confirmTextState && confirmTextState.isNotEmpty()

                                    if (!passwordValid) setPasswordError(true)
                                    if (!confirmValid) setConfirmError(true)

                                    if (passwordValid && confirmValid) {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(3)
                                        }
                                    }
                                }
                            )
                        }
                    }

                    3 -> {
                        Column {
                            Text(
                                text = "현재 재학중인 학교 이름을 입력해주세요",
                                color = colors.black,
                                style = typography.subTitle
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                JusicoolTextField(
                                    modifier = Modifier.weight(1f),
                                    label = "학교명",
                                    textState = schoolTextState,
                                    onTextChange = schoolOnTextChange,
                                    placeHolder = "학교명을 입력해주세요"
                                )

                                Box(
                                    modifier = Modifier
                                        .border(width = 1.dp, color = colors.main.copy(alpha = 0.5f), shape = RoundedCornerShape(size = 8.dp))
                                        .padding(15.dp)
                                ) {
                                    SearchIcon(
                                        modifier = Modifier.size(24.dp),
                                        tint = colors.main.copy(alpha = 0.5f)
                                    )
                                }
                            }

                            val sampleSchools = listOf(
                                School("광주소프트웨어마이스터고등학교", "광주광역시 광산구 하남산단6번로 107"),
                                School("서울과학고등학교", "서울특별시 노원구 공릉로 232"),
                                School("한성과학고등학교", "서울특별시 종로구 창경궁로 254"),
                                School("대전과학고등학교", "대전광역시 유성구 장동 23"),
                                School("부산과학고등학교", "부산광역시 남구 신선로 365")
                            )
                            SchoolList(schools = sampleSchools)

                            Spacer(modifier = Modifier.weight(1f))

                            JusicoolFilledButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = "다음",
                                state = ButtonState.Enable,
                                onClick = {
                                    /*TODO()*/
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}