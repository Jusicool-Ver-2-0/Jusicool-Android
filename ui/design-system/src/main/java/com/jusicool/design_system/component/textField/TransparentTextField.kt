package com.jusicool.design_system.component.textField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    textState: String,
    placeHolder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChange: (String) -> Unit,
    icon: @Composable () -> Unit = {}
) {
    JusicoolTheme { colors, _ ->
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = textState,
                onValueChange = { newText -> onTextChange(newText) },
                visualTransformation = visualTransformation,
                maxLines = 1,
                textStyle = TextStyle.Default,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box {
                            if (textState.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    color = colors.gray400,
                                    style = TextStyle.Default
                                )
                            }
                            innerTextField()
                        }

                        icon()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SimpleTextFieldPreview() {
    val (textState, onTextChange) = remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        TransparentTextField(
            textState = textState,
            onTextChange = onTextChange,
            placeHolder = "이메일을 입력해주세요"
        )
    }
}