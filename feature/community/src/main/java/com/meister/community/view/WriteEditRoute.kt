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
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon
import com.jusicool.design_system.theme.JusicoolTheme
import com.meister.community.component.WriteForm
import com.meister.community.viewModel.WriteEditViewModel
import com.meister.community.viewModel.uiState.WriteEditUiState

@Composable
internal fun WriteEditRoute(
    modifier: Modifier = Modifier,
    viewModel: WriteEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when (uiState) {
            is WriteEditUiState.Success -> popBackStack()
            is WriteEditUiState.Error -> {
                // Handle error state
            }

            is WriteEditUiState.Loading -> {
                // Handle loading state
            }
        }
    }

    WriteEditScreen(
        modifier = modifier,
        title = title,
        content = content,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onPostSubmit = viewModel::editPost,
        popBackStack = popBackStack
    )
}

@Composable
private fun WriteEditScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onPostSubmit: () -> Unit,
    popBackStack: () -> Unit,
) {
    val submitButtonState =
        if (title.isNotBlank() && content.isNotBlank()) ButtonState.Enable
        else ButtonState.Disable

    JusicoolTheme { colors, _ ->
        Column(modifier = modifier.fillMaxSize()) {
            JusicoolTopBar(
                modifier = Modifier.fillMaxWidth(),
                betweenText = "글 수정",
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
                    .padding(horizontal = 24.dp)
                    .padding(top = 12.dp, bottom = 20.dp),
            ) {
                WriteForm(
                    title = title,
                    content = content,
                    onTitleChange = onTitleChange,
                    onContentChange = onContentChange,
                )

                Spacer(modifier = Modifier.weight(1f))

                JusicoolFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "수정하기",
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
@Preview(showBackground = true)
private fun WriteEditScreenPreview() {
    WriteEditScreen(
        title = "제목 예시",
        content = "내용 예시",
        onTitleChange = {},
        onContentChange = {},
        onPostSubmit = {},
        popBackStack = {}
    )
}