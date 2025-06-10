package com.jusicool.chart.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.textField.TransparentTextField
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.utils.FormatPercent
import com.school_of_company.design_system.icon.ClarityArrowLineIcon

@Composable
internal fun StockSearchRoute(modifier: Modifier = Modifier) {

}

@Composable
private fun StockSearchScreen(
    modifier: Modifier = Modifier,
    searchTextState: String,
    popularKeyword: String,
    onSearchTextChange: (String) -> Unit,
    popUpBackStack: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchBox(
            modifier = Modifier.fillMaxWidth(),
            searchTextState = searchTextState,
            popularKeyword = popularKeyword,
            onSearchTextChange = onSearchTextChange,
            onArrowClick = popUpBackStack,
        )
    }
}

@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    searchTextState: String,
    popularKeyword: String,
    onSearchTextChange: (String) -> Unit,
    onArrowClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ClarityArrowLineIcon(
            modifier = Modifier
                .size(24.dp)
                .JusicoolClickable(onClick = onArrowClick),
        )

        TransparentTextField(
            modifier = Modifier.fillMaxWidth(),
            textState = searchTextState,
            onTextChange = onSearchTextChange,
            placeHolder = "'${popularKeyword}'를 검색해보세요",
        )
    }
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