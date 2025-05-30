package com.jusicool.signin.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import com.jusicool.signin.viewModel.SignInUiState
import com.jusicool.signin.viewModel.SignInViewModel

@Composable
fun SignInRoute(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val signInUiState by viewModel.signInUiState.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val isEmailError by viewModel.isEmailError.collectAsStateWithLifecycle()
    val isPasswordError by viewModel.isPasswordError.collectAsStateWithLifecycle()

    LaunchedEffect(signInUiState) {
        when (signInUiState) {
            is SignInUiState.Success -> onSignInClick()
            is SignInUiState.Error -> {
                // TODO: 에러 핸들링
            }
            else -> Unit
        }
    }

    SignInScreen(
        modifier = modifier,
        email = email,
        password = password,
        isEmailError = isEmailError,
        isPasswordError = isPasswordError,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = viewModel::onSignInClick
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    isEmailError: Boolean,
    isPasswordError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(start = 24.dp, top = 110.dp, end = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                modifier = Modifier
                    .width(220.dp)
                    .height(32.dp),
                painter = painterResource(id = com.jusicool.design_system.R.drawable.union),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "JusiCool로 간단하게 모의투자부터",
                style = typography.bodySmall,
                color = colors.gray600
            )

            Spacer(modifier = Modifier.height(60.dp))

            JusicoolTextField(
                label = "이메일",
                textState = email,
                onTextChange = onEmailChange,
                placeHolder = "이메일을 입력해주세요",
                isError = isEmailError,
                icon = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            JusicoolTextField(
                label = "비밀번호",
                textState = password,
                onTextChange = onPasswordChange,
                placeHolder = "비밀번호를 입력해주세요",
                isError = isPasswordError,
                helperText = "아이디와 비밀번호를 다시 확인해주세요",
                visualTransformation = PasswordVisualTransformation(),
                icon = {}
            )

            Spacer(modifier = Modifier.weight(1f))

            val isInputValid = email.isNotEmpty() && password.isNotEmpty()

            JusicoolFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                text = "로그인",
                state = if (isInputValid) ButtonState.Enable else ButtonState.Disable,
                onClick = onSignInClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "아직 계정이 없으신가요?",
                    style = typography.label,
                    color = colors.gray300
                )
                Text(
                    text = "회원가입",
                    style = typography.bodySmall,
                    color = colors.main,
                    modifier = Modifier.JusicoolClickable {
                        // TODO: 회원가입 화면으로 이동
                    }
                )
                Spacer(modifier = Modifier.height(84.dp))
            }
        }
    }
}

@Preview(showBackground = true, name = "초기 상태 (버튼 비활성화)")
@Composable
fun SignInScreenPreviewInitial() {
    SignInScreen(
        email = "",
        password = "",
        isEmailError = false,
        isPasswordError = false,
        onEmailChange = {},
        onPasswordChange = {},
        onSignInClick = {}
    )
}

@Preview(showBackground = true, name = "입력 완료 (버튼 활성화)")
@Composable
fun SignInScreenPreviewValid() {
    SignInScreen(
        email = "test@email.com",
        password = "password123@",
        isEmailError = false,
        isPasswordError = false,
        onEmailChange = {},
        onPasswordChange = {},
        onSignInClick = {}
    )
}

@Preview(showBackground = true, name = "에러 상태 (이메일, 비밀번호 문제)")
@Composable
fun SignInScreenPreviewError() {
    SignInScreen(
        email = "wrongemail",
        password = "sdasd",
        isEmailError = true,
        isPasswordError = true,
        onEmailChange = {},
        onPasswordChange = {},
        onSignInClick = {}
    )
}