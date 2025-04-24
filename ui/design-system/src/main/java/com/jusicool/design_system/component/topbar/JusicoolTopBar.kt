package com.jusicool.design_system.component.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.R
import com.jusicool.design_system.component.text.JusicoolSubjectTitleText
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun JusicoolTopBar(
    modifier: Modifier = Modifier,
    betweenText: String = "",
    startIcon: @Composable () -> Unit,
    endIcon: @Composable () -> Unit
) {
    JusicoolTheme { _, _ ->
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            startIcon()

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                JusicoolSubjectTitleText(subjectText = betweenText)
            }
            endIcon()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JusicoolTopbarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        JusicoolTopBar(
            startIcon = {
                Image(
                    painter = painterResource(id = R.drawable.union),
                    contentDescription = "Jusicool Logo",
                    modifier = Modifier
                        .width(116.00003.dp)
                        .height(16.80557.dp)
                )
            },
            endIcon = {
                Image(
                    painter = painterResource(id = R.drawable.swap_horizontal),
                    contentDescription = "swap",
                    modifier = Modifier
                        .size(8.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JusicoolTopbarPreview1() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        JusicoolTopBar(
            startIcon = {
                Image(
                    painter = painterResource(id = R.drawable.clarity_arrow_line),
                    contentDescription = "clarity_arrow_line",
                    modifier = Modifier
                        .width(18.dp)
                        .height(27.dp)
                )
            },
            betweenText = "뉴스",
            endIcon = {
                Spacer(modifier = Modifier.size(24.dp))
            }
        )
    }
}