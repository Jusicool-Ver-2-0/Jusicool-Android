package com.jusicool.design_system.theme.color

import androidx.compose.ui.graphics.Color

abstract class ColorTheme {

    // Main ColorTheme
    abstract val main: Color

    // Error(Red) ColorTheme
    abstract val error: Color

    // Greyscale ColorTheme
    abstract val gray800: Color
    abstract val gray700: Color
    abstract val gray600: Color
    abstract val gray500: Color
    abstract val gray400: Color
    abstract val gray300: Color
    abstract val gray200: Color
    abstract val gray100: Color
    abstract val gray50: Color

    abstract val black: Color
    abstract val white: Color
}