package com.jusicool.design_system.theme

import androidx.compose.runtime.Composable
import com.jusicool.design_system.theme.color.ColorTheme
import com.jusicool.design_system.theme.color.JusicoolColor

@Composable
fun JusicoolTheme(
    colors: JusicoolColor = JusicoolColor,
    typography: JusicoolTypography = JusicoolTypography,
    content: @Composable (colors: ColorTheme, typography: JusicoolTypography) -> Unit
) {
    content(colors, typography)
}