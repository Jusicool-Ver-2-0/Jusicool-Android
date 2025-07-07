package com.meister.community.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.TransparentTextField
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.icon.HeartIcon
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon
import com.jusicool.design_system.icon.LetsIconsSettingFillIcon
import com.jusicool.design_system.icon.SendIcon
import com.jusicool.design_system.theme.JusicoolTheme
import com.meister.community.component.CommonAlertDialog
import com.meister.community.viewModel.DetailPostViewModel
import com.meister.community.viewModel.uiState.CommunityDetailUiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
internal fun WriteDetailRoute(
    modifier: Modifier = Modifier,
    communityName: String,
    navigateToWriteModify: (id: String) -> Unit,
    popBackStack: () -> Unit,
    viewModel: DetailPostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val commentState by viewModel.commentState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {}
        uiState.errorMessage != null -> {}
        else -> {
            WriteDetailScreen(
                modifier = modifier,
                communityName = communityName,
                commentState = commentState,
                isMyWrite = true,
                uiState = uiState,
                toggleLike = viewModel::toggleLike,
                deletePost = viewModel::deletePost,
                postComment = viewModel::postComment,
                onCommentStateChange = viewModel::onCommentStateChange,
                popBackStack = popBackStack,
                navigateToWriteModify = { navigateToWriteModify("1") },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WriteDetailScreen(
    modifier: Modifier = Modifier,
    communityName: String,
    commentState: String,
    isMyWrite: Boolean,
    uiState: CommunityDetailUiState,
    toggleLike: () -> Unit,
    deletePost: () -> Unit,
    postComment: () -> Unit,
    onCommentStateChange: (String) -> Unit,
    popBackStack: () -> Unit,
    navigateToWriteModify: () -> Unit,
) {
    var deleteDialog by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
        ) {
            CommunityDetailTopBar(
                communityName = communityName,
                isMyWrite = isMyWrite,
                onBackClick = popBackStack,
                onSettingClick = { coroutineScope.launch { bottomSheetState.show() } },
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            ) {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = uiState.title,
                        style = typography.titleSmall,
                        color = colors.black
                    )
                }

                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = uiState.content,
                        style = typography.bodySmall,
                        color = colors.black
                    )
                }

                item {
                    LikeButton(
                        isLiked = uiState.isLiked,
                        likeCount = uiState.likeCount,
                        toggleLike = toggleLike
                    )
                }

                item {
                    Divider(
                        thickness = 1.dp,
                        color = colors.gray100,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    CommentInput(
                        commentState = commentState,
                        onCommentChange = onCommentStateChange,
                        onPostComment = postComment
                    )
                }

                commentList(comments = uiState.comments)
            }

            if (bottomSheetState.isVisible) {
                CommunityDetailBottomSheet(
                    bottomSheetState = bottomSheetState,
                    onDismissRequest = { coroutineScope.launch { bottomSheetState.hide() } },
                    onModifyClick = navigateToWriteModify,
                    onDeleteClick = { deleteDialog = true }
                )
            }

            if (deleteDialog) {
                CommonAlertDialog(
                    contentText = "글을 삭제할까요?",
                    onDismissRequest = { deleteDialog = false },
                    onConfirm = {
                        deletePost()
                        deleteDialog = false
                    }
                )
            }
        }
    }
}

@Composable
}

@Composable
private fun LikeButton(
    isLiked: Boolean,
    likeCount: Int,
    toggleLike: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        val contentColor = if (isLiked) colors.white else colors.gray400
        val backGroundColor = if (isLiked) colors.main else colors.white
        val outlineColor = if (isLiked) colors.main else colors.gray100

        Row(
            modifier = Modifier
                .JusicoolClickable(onClick = toggleLike)
                .background(backGroundColor, RoundedCornerShape(12.dp))
                .border(1.dp, outlineColor, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HeartIcon(tint = contentColor)

            Text(
                text = likeCount.toString(),
                style = typography.label,
                color = contentColor,
            )
        }
    }
}

private fun LazyListScope.commentList(comments: PersistentList<String>) {
    items(
        items = comments,
        key = { it },
    ) { comment ->
        CommentItem(comment)
    }
}

@Composable
private fun CommentItem(comment: String) {
    JusicoolTheme { _, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = comment,
                style = typography.bodyMedium
            )

            Text(
                text = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다...",
                style = typography.bodyMedium
            )
        }
    }
}


@Composable
private fun CommentInput(
    commentState: String,
    onCommentChange: (String) -> Unit,
    onPostComment: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, colors.gray200, RoundedCornerShape(12.dp))
                .padding(end = 16.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val sendIconColor = if (commentState.isEmpty()) colors.gray400 else colors.main

            TransparentTextField(
                modifier = Modifier.weight(1f),
                textState = commentState,
                onTextChange = onCommentChange,
                placeHolder = "댓글을 작성해보세요",
                textStyle = typography.bodySmall.copy(color = colors.black),
                placeholderStyle = typography.bodySmall.copy(color = colors.gray400),
            )

            SendIcon(
                tint = sendIconColor,
                modifier = Modifier
                    .size(32.dp)
                    .JusicoolClickable(onClick = onPostComment),
            )
        }
    }
}


@Composable
private fun CommunityDetailTopBar(
    communityName: String,
    isMyWrite: Boolean,
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit
) {
    JusicoolTopBar(
        modifier = Modifier.fillMaxWidth(),
        betweenText = "$communityName 커뮤니티",
        startIcon = {
            LeftClarityArrowLineIcon(
                modifier = Modifier
                    .size(24.dp)
                    .JusicoolClickable(onClick = onBackClick),
            )
        },
        endIcon = {
            if (isMyWrite) {
                LetsIconsSettingFillIcon(
                    modifier = Modifier
                        .size(24.dp)
                        .JusicoolClickable(onClick = onSettingClick),
                )
            } else {
                Spacer(modifier = Modifier.size(24.dp))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommunityDetailBottomSheet(
    bottomSheetState: SheetState,
    onDismissRequest: () -> Unit,
    onModifyClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    JusicoolTheme { colors, typography ->
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = bottomSheetState,
            containerColor = colors.white,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            dragHandle = null,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 32.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "수정하기",
                    style = typography.bodySmall,
                    color = colors.gray600,
                    modifier = Modifier.JusicoolClickable(onClick = onModifyClick)
                )

                Text(
                    text = "삭제하기",
                    style = typography.bodySmall,
                    color = colors.error,
                    modifier = Modifier.JusicoolClickable(onClick = onDeleteClick)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteDetailScreenPreview() {
    val dummyUiState = CommunityDetailUiState(
        title = "커뮤니티는공통의관심사목표가치\n" +
                "혹은지리적",
        content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인들이소속감을느끼고상호작용하며협력하는장소로서중요한역할을합니다커뮤니티는온라인과오프라인에서모두존재할수있으며그형태와목적은다양합니다커뮤니티의주요기능중하나는소속감을제공하는것입니다개인들은커뮤니티를통해자신이속한그룹의일원으로서인정받고이는정체성과자존감을높이는데기여합니다예를들어지역주민모임취미동호회직장내동료그룹등은모두개인들이소속감을느끼게하는커뮤니티의예입니다또한커뮤니티는정보와자원의교환을촉진합니다구성원들은지식경험자원등을공유하며상호도움을주고받습니다이는개인의문제해결능력을높이고새로운아이디어와관점을얻는데도움이됩니다예를들어학술커뮤니티에서는연구자들이최신연구결과를공유하고토론하",
        isLiked = true,
        likeCount = 10,
        comments = persistentListOf("홍길동", "두 번째 댓글", "세 번째 댓글"),
    )

    WriteDetailScreen(
        communityName = "커뮤니티 이름",
        commentState = "댓글 작성 중...",
        isMyWrite = true,
        uiState = dummyUiState,
        toggleLike = {},
        deletePost = {},
        postComment = {},
        onCommentStateChange = {},
        popBackStack = {},
        navigateToWriteModify = {},
    )
}
