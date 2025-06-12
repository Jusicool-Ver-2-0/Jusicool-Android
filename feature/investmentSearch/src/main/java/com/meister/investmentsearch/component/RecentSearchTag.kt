package com.meister.investmentsearch.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.FormatPercent
import com.school_of_company.design_system.icon.XIcon


@Composable
internal fun RecentSearchTag(
    modifier: Modifier = Modifier,
    investmentName: String,
    investmentChangeRate: Double,
    onClearClick: () -> Unit
) {
    val isPlus = investmentChangeRate > 0

    JusicoolTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(color = colors.gray100, shape = RoundedCornerShape(size = 14.dp))
                .padding(8.dp),
        ) {
            Text(
                text = investmentName,
                style = typography.label,
                color = colors.gray600,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = FormatPercent.format(investmentChangeRate),
                style = typography.label,
                color = if (isPlus) colors.error else colors.main,
            )
            Spacer(modifier = Modifier.width(8.dp))
            XIcon(
                modifier = Modifier
                    .size(24.dp)
                    .JusicoolClickable(
                        onClick = onClearClick
                    )
            )
        }
    }
}

@Preview
@Composable
private fun RecentSearchTagPreview() {
    RecentSearchTag(
        modifier = Modifier,
        investmentName = "삼성전자",
        investmentChangeRate = 12.1,
        onClearClick = {},
    )
}
