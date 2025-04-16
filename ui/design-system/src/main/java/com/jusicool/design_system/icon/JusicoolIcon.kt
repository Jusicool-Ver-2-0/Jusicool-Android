package com.school_of_company.design_system.icon

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.design_system.R

@Composable
fun AccountImage(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.account),
        contentDescription = stringResource(id = R.string.account_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun ClarityArrowLineIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.clarity_arrow_line),
        contentDescription = stringResource(id = R.string.clarity_arrow_line_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun RightArrowIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.right_arrow),
        contentDescription = stringResource(id = R.string.right_arrow_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun ChartLineIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.chart_line),
        contentDescription = stringResource(id = R.string.chart_line_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun FlatCylinderImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.flat_cylinder_3),
        contentDescription = stringResource(id = R.string.flat_cylinder_description),
        modifier = modifier
    )
}

@Composable
fun LetsIconsSettingFillIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.lets_icons_setting_fill),
        contentDescription = stringResource(id = R.string.lets_icons_setting_fill_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.search),
        contentDescription = stringResource(id = R.string.search_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun PieChartFilledIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.pie_chartfilled),
        contentDescription = stringResource(id = R.string.pie_chartfilled_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun UnionIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.union),
        contentDescription = stringResource(id = R.string.union_description),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun MaterialSymbolsNewsOutlineIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = R.drawable.material_symbols_news_outline),
        contentDescription = stringResource(id = R.string.material_symbols_news_outline_description),
        modifier = modifier,
        tint = tint
    )
}