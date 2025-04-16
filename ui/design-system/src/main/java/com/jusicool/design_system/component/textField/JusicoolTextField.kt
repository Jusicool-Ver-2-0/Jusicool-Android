package com.jusicool.design_system.component.textField

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun JusicoolTextField(
    modifier: Modifier = Modifier,
    textState: String,
    label: String,
    placeHolder: String,
    helperText: String = "",
    errorText:String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChange: (String) -> Unit,
    icon: @Composable () -> Unit
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                color = if(isError) colors.error else colors.black,
                style = typography.bodySmall
            )

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color =  if(isError) colors.error else colors.gray100 ,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(16.dp),
                value = textState,
                onValueChange = { newText -> onTextChange(newText) },
                visualTransformation =visualTransformation,
                maxLines = 1,
                textStyle = typography.bodySmall,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box {
                            if(textState.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    color =  if(isError) colors.error else colors.gray300 ,
                                    style = typography.bodySmall
                                )
                            }
                            innerTextField()
                        }

                        icon()
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = helperText,
                    color = colors.error,
                    style = typography.label
                )

                Text(
                    text = errorText,
                    color = colors.main,
                    style = typography.label
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JusicoolTextFieldPreview() {
    val (textState, onTextChange) = remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        JusicoolTextField(
            label = "이메일",
            textState = textState,
            onTextChange = onTextChange,
            placeHolder = "이메일을 입력해주세요",
            icon = {}
        )

        JusicoolTextField(
            label = "이메일",
            textState = textState,
            isError = true,
            onTextChange = onTextChange,
            placeHolder = "이메일을 입력해주세요",
            errorText = "이메일 형식을 다시 입력해주세요",
            icon = {}
        )

        JusicoolTextField(
            label = "이메일",
            textState = textState,
            isError = true,
            onTextChange = onTextChange,
            placeHolder = "이메일을 입력해주세요",
            helperText = "이메일 수정하기",
            icon = {}
        )
    }
}