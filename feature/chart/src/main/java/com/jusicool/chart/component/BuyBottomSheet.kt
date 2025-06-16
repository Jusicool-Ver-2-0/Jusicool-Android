package com.jusicool.chart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.button.JusicoolOutlinedButton
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.formatMoney

@Composable
fun BuyBottomSheet(
    modifier: Modifier = Modifier,
    name: String,
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = colors.white, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = name,
                color = colors.black,
                style = typography.subTitle
            )


            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                JusicoolOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "현재가 구매하기",
                    outlineColor = colors.error,
                    onClick = {}
                )


                JusicoolOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "예약 구매하기",
                    outlineColor = colors.error,
                    onClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BuyBottomSheetPreview() {
    BuyBottomSheet(name = "마이크로소프트",)
}