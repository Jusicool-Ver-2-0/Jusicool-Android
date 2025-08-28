package com.jusicool.signup.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun SchoolListItem(
    modifier: Modifier = Modifier,
    schoolName: String,
    address: String,
    selected: Boolean
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = if(selected) colors.main.copy(alpha = 0.5f) else colors.gray100, shape = RoundedCornerShape(size = 8.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(color = colors.gray100, shape = RoundedCornerShape(size = 4.dp))
                        .padding(horizontal = 15.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "학교명",
                        color = colors.gray600,
                        style = typography.label
                    )
                }

                Text(
                    text = schoolName,
                    color = colors.black,
                    style = typography.label
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(color = colors.gray100, shape = RoundedCornerShape(size = 4.dp))
                        .padding(horizontal = 20.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "주소",
                        color = colors.gray600,
                        style = typography.label
                    )
                }

                Text(
                    text = address,
                    color = colors.black,
                    style = typography.label
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SchoolListItemPreview() {
    SchoolListItem(
        schoolName = "보길중학교",
        address = "보길도",
        selected = true
    )
}