package com.meister.community.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.textField.TransparentTextField
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
internal fun WriteForm(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            TransparentTextField(
                textState = title,
                placeHolder = "제목을 입력하세요",
                onTextChange = onTitleChange,
                textStyle = typography.titleSmall.copy(color = colors.black),
                placeholderStyle = typography.titleSmall.copy(color = colors.gray200),
            )

            TransparentTextField(
                textState = content,
                placeHolder = "내용을 입력하세요",
                onTextChange = onContentChange,
                textStyle = typography.bodySmall.copy(color = colors.black),
                placeholderStyle = typography.bodySmall.copy(color = colors.gray200),
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun WritePostFormPreview() {
    WriteForm(
        title = "",
        content = "",
        onTitleChange = {},
        onContentChange = {}
    )
}