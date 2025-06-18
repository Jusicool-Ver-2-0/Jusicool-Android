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
fun SellBottomSheet(
    modifier: Modifier = Modifier,
    name: String,
    quantity: Int,
    type: String,
    navigateToSell: (String, String, Int) -> Unit,
    navigateToReserveSell: (String, String, Int) -> Unit,
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
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                JusicoolOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "현재가 판매하기",
                    outlineColor = colors.main,
                    onClick = {navigateToSell(name,type,quantity)}
                )


                JusicoolOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "예약 판매하기",
                    outlineColor = colors.main,
                    onClick = {navigateToReserveSell(name,type,quantity)}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SellBottomSheetPreview() {
    SellBottomSheet(
        name = "마이크로소프트",
        quantity = 1,
        type = "",
        navigateToSell = { _,_,_ -> },
        navigateToReserveSell = { _,_,_ -> }
    )
}