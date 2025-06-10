package com.jusicool.chart.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.FormatPercent

@Composable
internal fun StockSearchRoute(modifier: Modifier = Modifier) {
    StockSearchScreen(modifier = modifier)
}

@Composable
private fun StockSearchScreen(modifier: Modifier = Modifier) {

}

@Composable
private fun SearchKeywordRow(
    modifier: Modifier = Modifier,
    order: Int,
    keyword: String,
    changeRate: Double,
) {
    val isPlus = changeRate > 0

    JusicoolTheme { colors, typography ->
        Row(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "$order",
                    style = typography.bodyMedium,
                )

                Spacer(Modifier.width(50.dp))

                Text(
                    text = keyword,
                    style = typography.bodySmall,
                )
            }

            Text(
                text = FormatPercent.format(changeRate),
                style = typography.bodySmall,
                color = if (isPlus) colors.error else colors.main,
            )
        }
    }
}

@Preview
@Composable
private fun SearchKeywordRowPreview() {
    SearchKeywordRow(
        modifier = Modifier,
        order = 2,
        keyword = "삼성전자",
        changeRate = 12.1,
    )
}