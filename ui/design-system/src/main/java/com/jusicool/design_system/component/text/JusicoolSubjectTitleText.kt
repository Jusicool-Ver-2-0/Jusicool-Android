package com.jusicool.design_system.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jusicool.design_system.theme.JusicoolTheme

@Composable
fun JusicoolSubjectTitleText(
    modifier: Modifier = Modifier,
    subjectText: String
) {
    JusicoolTheme { colors, typography ->
        Text(
            modifier = modifier,
            text = subjectText,
            color = colors.black,
            style = typography.subTitle,
            maxLines = 1,
        )
    }
}