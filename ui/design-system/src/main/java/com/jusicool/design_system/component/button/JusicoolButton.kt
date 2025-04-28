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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.design_system.component.button.state.ButtonState

@Composable
fun JusicoolFilledButton(
    modifier: Modifier = Modifier,
    text: String,
    state: ButtonState = ButtonState.Enable,
    filledColor: Color? =null,
    onClick: () -> Unit,
) {
    JusicoolTheme { colors, typography ->
        val enabledState: (buttonState: ButtonState) -> Boolean = {
            it == ButtonState.Enable
        }

        val containerColor = when (state) {
            ButtonState.Enable -> filledColor ?: colors.main
            ButtonState.Disable -> colors.gray300
        }

        Button(
            modifier = modifier,
            onClick = onClick,
            enabled = enabledState(state),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = colors.white,
                disabledContainerColor = containerColor,
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

@Preview(showBackground = true)
@Composable
private fun JusicoolButtonPreview() {
    JusicoolTheme { colors, typography ->
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            JusicoolFilledButton(
                text = "다음",
                state = ButtonState.Enable,
                onClick = {}
            )
            JusicoolFilledButton(
                text = "다음",
                state = ButtonState.Enable,
                filledColor = colors.error,
                onClick = {}
            )
            JusicoolFilledButton(
                text = "다음",
                state = ButtonState.Disable,
                onClick = {}
            )
        }
    }
}