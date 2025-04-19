package com.jusicool.design_system.component.button.state

sealed class ButtonState {
    object Enable: ButtonState()
    object Disable: ButtonState()
}