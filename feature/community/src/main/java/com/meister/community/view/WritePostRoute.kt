package com.meister.community.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.TransparentTextField
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.meister.community.viewModel.WritePostViewModel
import com.meister.community.viewModel.uiState.WritePostUiState
import com.school_of_company.design_system.icon.LeftClarityArrowLineIcon

@Composable
internal fun WritePostRoute(
    modifier: Modifier = Modifier,
    viewModel: WritePostViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when (uiState) {
            is WritePostUiState.Success -> onBackPressed()
            is WritePostUiState.Error -> {
                // Handle error state
            }

            is WritePostUiState.Loading -> {
                // Handle loading state
            }
        }
    }

    WritePostScreen(
        modifier = modifier,
        title = title,
        content = content,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onPostSubmit = viewModel::submitPost,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun WritePostScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onPostSubmit: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val submitButtonState =
        if (title.isNotBlank() && content.isNotBlank()) ButtonState.Enable
        else ButtonState.Disable
        val scrollState = rememberScrollState()

    JusicoolTheme { colors, typography ->
        Column(modifier = modifier.fillMaxSize()) {
            JusicoolTopBar(
                modifier = Modifier.fillMaxWidth(),
                betweenText = "글 작성",
                startIcon = {
                    LeftClarityArrowLineIcon(
                        modifier = Modifier
                            .size(24.dp)
                            .JusicoolClickable(onClick = onBackPressed),
                    )
                },
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
                    .padding(top = 12.dp, bottom = 20.dp),
            ) {
                WritePostForm(
                    title = title,
                    content = content,
                    onTitleChange = onTitleChange,
                    onContentChange = onContentChange,
                )
                
                Spacer(modifier = Modifier.weight(1f))

                JusicoolFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "올리기",
                    state = submitButtonState,
                    filledColor = colors.main,
                    filledDisableColor = colors.gray400,
                    onClick = onPostSubmit
                )
            }
        }
    }
}

@Composable
private fun WritePostForm(
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
private fun WritePostScreenPreview() {
    WritePostScreen(
        title = "제목 예시",
        content = "내용 예시",
        onTitleChange = {},
        onContentChange = {},
        onPostSubmit = {},
        onBackPressed = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun WritePostFormPreview() {
    WritePostForm(
        title = "",
        content = "",
        onTitleChange = {},
        onContentChange = {}
    )
}
