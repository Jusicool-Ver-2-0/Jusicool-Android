package com.example.signin.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.JusicoolTextField
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    initialEmail: String = "",
    initialPassword: String = "",
    emailErrorPreview: Boolean = false,
    passwordErrorPreview: Boolean = false
) {
    JusicoolTheme { colors, typography ->
        var email by remember { mutableStateOf(initialEmail) }
        var password by remember { mutableStateOf(initialPassword) }
        var emailError by remember { mutableStateOf(emailErrorPreview) }
        var passwordError by remember { mutableStateOf(passwordErrorPreview) }
        var triedLogin by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(start = 24.dp, top = 110.dp, end = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = com.jusicool.design_system.R.drawable.union),
                contentDescription = null,
                modifier = Modifier
                    .width(220.dp)
                    .height(32.dp)
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
                onTextChange = {
                    email = it
                    if (triedLogin) {
                        emailError = !isValidEmail(email)
                    }
                },
                placeHolder = "이메일을 입력해주세요",
                isError = emailError,
                errorText = if (emailError) "이메일 형식을 다시 입력해주세요" else "",
                icon = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            JusicoolTextField(
                label = "비밀번호",
                textState = password,
                onTextChange = {
                    password = it
                    if (triedLogin) {
                        passwordError = password.isEmpty()
                    }
                },
                placeHolder = "비밀번호를 입력해주세요",
                isError = passwordError,
                errorText = if (passwordError) "비밀번호를 입력해주세요" else "",
                visualTransformation = PasswordVisualTransformation(),
                icon = {}
            )

            Spacer(modifier = Modifier.weight(1f))

            val isInputValid = email.isNotEmpty() && password.isNotEmpty()

            JusicoolFilledButton(
                text = "로그인",
                state = if (isInputValid) ButtonState.Enable else ButtonState.Disable,
                onClick = {
                    triedLogin = true
                    emailError = !isValidEmail(email)
                    passwordError = password.isEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
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

private fun isValidEmail(email: String): Boolean {
    return email.contains("@") && email.contains(".")
}

@Preview(showBackground = true, name = "초기 상태 (버튼 비활성화)")
@Composable
fun SignInScreenPreviewInitial() {
    SignInScreen()
}

@Preview(showBackground = true, name = "입력 완료 (버튼 활성화)")
@Composable
fun SignInScreenPreviewValid() {
    SignInScreen(
        initialEmail = "test@email.com",
        initialPassword = "password123@"
    )
}

@Preview(showBackground = true, name = "에러 상태 (이메일, 비밀번호 문제)")
@Composable
fun SignInScreenPreviewError() {
    SignInScreen(
        initialEmail = "wrongemail",
        initialPassword = "sdasd",
        emailErrorPreview = true,
        passwordErrorPreview = true
    )
}