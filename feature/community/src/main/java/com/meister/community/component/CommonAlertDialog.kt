package com.meister.community.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
internal fun CommonAlertDialog(
    modifier: Modifier = Modifier,
    contentText: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    JusicoolTheme { colors, typography ->
        Dialog(onDismissRequest = onDismissRequest) {
            Column(
                modifier = modifier
                    .background(colors.white, shape = RoundedCornerShape(12.dp))
                    .height(IntrinsicSize.Max)
                    .width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = contentText,
                    style = typography.bodySmall,
                    color = colors.black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
                )

                Divider(color = colors.gray200, thickness = 1.dp)

                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDismissRequest() }
                            .padding(vertical = 8.dp, horizontal = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "취소",
                            color = colors.error,
                            textAlign = TextAlign.Center
                        )
                    }

                    Divider(
                        color = colors.gray200,
                        modifier = Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onConfirm() }
                            .padding(vertical = 8.dp, horizontal = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "확인",
                            color = colors.main,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CommonAlertDialogContentPreview() {
    CommonAlertDialog(
        contentText = "정말 삭제하시겠습니까?",
        onDismissRequest = {},
        onConfirm = {}
    )
}
