package com.jusicool.design_system.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.design_system.component.button.state.ButtonState

@Composable
fun JusicoolFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState.Enable,
    onClick: () -> Unit,
) {
    JusicoolTheme { colors, typography ->
        val enabledState: (buttonState: ButtonState) -> Boolean = {
            it == ButtonState.Enable
        }

        Button(
            modifier = modifier,
            onClick = onClick,
            enabled = enabledState(state),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.main,
                contentColor = colors.white,
                disabledContainerColor = colors.gray300,
                disabledContentColor = colors.gray600
            )
        ) {
            Text(
                text = text,
                style = typography.bodyMedium
            )
        }
    }
}

@Composable
fun JusicoolOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState.Enable,
    onClick: () -> Unit,
) {
    JusicoolTheme { colors, typography ->
        val enabledState: (buttonState: ButtonState) -> Boolean = {
            it == ButtonState.Enable
        }

        OutlinedButton(
            modifier = modifier,
            onClick = onClick,
            enabled = enabledState(state),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colors.main,
                disabledContentColor = colors.gray300
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (enabledState(state)) colors.main else colors.gray200
            )
        ) {
            Text(
                text = text,
                style = typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JusicoolButtonPreview() {
    JusicoolTheme { _, _ ->
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            JusicoolFilledButton(
                text = "다음",
                state = ButtonState.Enable,
                onClick = {}
            )
            JusicoolOutlinedButton(
                text = "다음",
                state = ButtonState.Enable,
                onClick = {}
            )
            JusicoolFilledButton(
                text = "다음",
                state = ButtonState.Disable,
                onClick = {}
            )
            JusicoolOutlinedButton(
                text = "다음",
                state = ButtonState.Disable,
                onClick = {}
            )
        }
    }
}