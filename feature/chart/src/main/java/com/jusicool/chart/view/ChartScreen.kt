package com.jusicool.chart.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.chart.component.CandleChart
import com.jusicool.chart.component.CommunityCard
import com.jusicool.chart.component.NewsCard
import com.jusicool.chart.component.PriceBarChart
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.chart.CandleChartModel
import com.jusicool.model.chart.ChartInformationModel
import com.jusicool.model.chart.ChartPriceModel
import com.jusicool.model.community.CommunityModel
import com.jusicool.model.news.NewsModel
import com.school_of_company.design_system.icon.ClarityArrowLineIcon
import com.school_of_company.design_system.icon.LetsIconsSettingFillIcon

@Composable
fun ChartScreen(
    modifier: Modifier = Modifier,
    chartInformation: ChartInformationModel,
    candles: List<CandleChartModel>,
    price: ChartPriceModel,
    news: List<NewsModel>,
    community: List<CommunityModel>
) {
    val scrollState = rememberScrollState()
    var selectedInfo by remember { mutableStateOf("종목 정보") }

    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = colors.white)
        ) {
            JusicoolTopBar(
                modifier = Modifier.fillMaxWidth(),
                betweenText = chartInformation.name,
                startIcon = { ClarityArrowLineIcon(modifier = Modifier.JusicoolClickable { /*TODO()*/ }) },
                endIcon = { LetsIconsSettingFillIcon(modifier = Modifier.JusicoolClickable { /*TODO()*/ }) }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "${"%,d".format(chartInformation.price)}원",
                    color = colors.black,
                    style = typography.titleMedium
                )

                CandleChart(
                    modifier = Modifier.fillMaxWidth(),
                    candles = candles
                )

                JusicoolFilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    text = "구매하기",
                    state = ButtonState.Enable,
                    filledColor = colors.error,
                    onClick = {}
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.JusicoolClickable {
                                selectedInfo = "종목 정보"
                            },
                            text = "종목 정보",
                            color = if (selectedInfo == "종목 정보") colors.black else colors.gray400,
                            style = typography.subTitle
                        )

                        Text(
                            modifier = Modifier.JusicoolClickable {
                                selectedInfo = "시세"
                            },
                            text = "시세",
                            color = if (selectedInfo == "시세") colors.black else colors.gray400,
                            style = typography.subTitle
                        )

                        Text(
                            modifier = Modifier.JusicoolClickable {
                                selectedInfo = "뉴스"
                            },
                            text = "뉴스",
                            color = if (selectedInfo == "뉴스") colors.black else colors.gray400,
                            style = typography.subTitle
                        )

                        Text(
                            modifier = Modifier.JusicoolClickable {
                                selectedInfo = "커뮤니티"
                            },
                            text = "커뮤니티",
                            color = if (selectedInfo == "커뮤니티") colors.black else colors.gray400,
                            style = typography.subTitle
                        )
                    }

                    when (selectedInfo) {
                        "종목 정보" -> {
                            Text(
                                text = chartInformation.information,
                                color = colors.black,
                                style = typography.bodySmall
                            )
                        }
                        "시세" -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment =Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                PriceBarChart(
                                    price = price
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "시작가",
                                                color = colors.black,
                                                style = typography.bodyMedium
                                            )

                                            Text(
                                                text = "${"%,d".format(price.dayOpen)}원",
                                                color = colors.gray600,
                                                style = typography.label
                                            )
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "종가",
                                                color = colors.black,
                                                style = typography.bodyMedium
                                            )

                                            Text(
                                                text = "${"%,d".format(price.dayClose)}원",
                                                color = colors.gray600,
                                                style = typography.label
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .fillMaxHeight()
                                            .background(color = colors.gray100, shape = RoundedCornerShape(size = 1.dp))
                                    )

                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "거래량",
                                                color = colors.black,
                                                style = typography.bodyMedium
                                            )

                                            Text(
                                                text = "${"%,d".format(price.tradingVolume)}개",
                                                color = colors.gray600,
                                                style = typography.label
                                            )
                                        }

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "거래대금",
                                                color = colors.black,
                                                style = typography.bodyMedium
                                            )

                                            Text(
                                                text = "${"%,d".format(price.tradingPrice)}원",
                                                color = colors.gray600,
                                                style = typography.label
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        "뉴스" -> {
                            NewsCard(
                                news = news
                            )
                        }
                        "커뮤니티" -> {
                            CommunityCard(
                                community = community
                            )
                        }
                        else -> { }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChartScreenPreview() {
    val mockCandles = listOf(
        CandleChartModel(open = 65000, close = 65500, shadowHigh = 65800, shadowLow = 64800),
        CandleChartModel(open = 65500, close = 66000, shadowHigh = 66300, shadowLow = 65200),
        CandleChartModel(open = 66000, close = 67000, shadowHigh = 67200, shadowLow = 65800),
        CandleChartModel(open = 67000, close = 67500, shadowHigh = 67800, shadowLow = 66600),
        CandleChartModel(open = 67500, close = 68500, shadowHigh = 69000, shadowLow = 67300),
        CandleChartModel(open = 68500, close = 67800, shadowHigh = 68800, shadowLow = 67500),
        CandleChartModel(open = 67800, close = 68000, shadowHigh = 68400, shadowLow = 67600),
        CandleChartModel(open = 68000, close = 67400, shadowHigh = 68200, shadowLow = 67000),
        CandleChartModel(open = 67400, close = 68000, shadowHigh = 68400, shadowLow = 67200),
        CandleChartModel(open = 68000, close = 69000, shadowHigh = 69500, shadowLow = 67800),
        CandleChartModel(open = 69000, close = 68800, shadowHigh = 69300, shadowLow = 68500),
        CandleChartModel(open = 68800, close = 68700, shadowHigh = 69000, shadowLow = 68200),
        CandleChartModel(open = 68700, close = 69000, shadowHigh = 69200, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 70000, shadowHigh = 70500, shadowLow = 68800),
        CandleChartModel(open = 70000, close = 71000, shadowHigh = 71500, shadowLow = 69800),
        CandleChartModel(open = 71000, close = 72000, shadowHigh = 72500, shadowLow = 70700),
        CandleChartModel(open = 72000, close = 70500, shadowHigh = 72300, shadowLow = 70000),
        CandleChartModel(open = 70500, close = 69500, shadowHigh = 70800, shadowLow = 69000),
        CandleChartModel(open = 69500, close = 70000, shadowHigh = 70200, shadowLow = 69300),
        CandleChartModel(open = 70000, close = 69800, shadowHigh = 70100, shadowLow = 69000),
        CandleChartModel(open = 69800, close = 69000, shadowHigh = 69900, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 68800, shadowHigh = 69400, shadowLow = 68400),
        CandleChartModel(open = 68800, close = 68000, shadowHigh = 69000, shadowLow = 67800),
        CandleChartModel(open = 68000, close = 68300, shadowHigh = 68500, shadowLow = 67800),
        CandleChartModel(open = 68300, close = 68100, shadowHigh = 68600, shadowLow = 67500),
        CandleChartModel(open = 68100, close = 69000, shadowHigh = 69300, shadowLow = 67900),
        CandleChartModel(open = 69000, close = 69200, shadowHigh = 69500, shadowLow = 68800),
        CandleChartModel(open = 69200, close = 69800, shadowHigh = 70000, shadowLow = 69000),
        CandleChartModel(open = 69800, close = 69300, shadowHigh = 69900, shadowLow = 68800),
        CandleChartModel(open = 69300, close = 69500, shadowHigh = 69700, shadowLow = 69000),
        CandleChartModel(open = 69500, close = 69000, shadowHigh = 69600, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 68400, shadowHigh = 69200, shadowLow = 68000),
        CandleChartModel(open = 68400, close = 67500, shadowHigh = 68600, shadowLow = 67000),
        CandleChartModel(open = 67500, close = 66800, shadowHigh = 67800, shadowLow = 66500),
        CandleChartModel(open = 66800, close = 67200, shadowHigh = 67500, shadowLow = 66600),
        CandleChartModel(open = 67200, close = 66500, shadowHigh = 67300, shadowLow = 66200),
        CandleChartModel(open = 66500, close = 67000, shadowHigh = 67300, shadowLow = 66000),
        CandleChartModel(open = 67000, close = 67500, shadowHigh = 67800, shadowLow = 66700),
        CandleChartModel(open = 67500, close = 68000, shadowHigh = 68200, shadowLow = 67000),
        CandleChartModel(open = 68000, close = 67800, shadowHigh = 68500, shadowLow = 67500),
        CandleChartModel(open = 67800, close = 68200, shadowHigh = 68400, shadowLow = 67600),
        CandleChartModel(open = 68200, close = 68800, shadowHigh = 69000, shadowLow = 68000),
        CandleChartModel(open = 68800, close = 68500, shadowHigh = 69000, shadowLow = 68200),
        CandleChartModel(open = 68500, close = 69000, shadowHigh = 69200, shadowLow = 68300),
        CandleChartModel(open = 69000, close = 70000, shadowHigh = 70500, shadowLow = 68800),
        CandleChartModel(open = 70000, close = 70500, shadowHigh = 71000, shadowLow = 69800),
        CandleChartModel(open = 70500, close = 70200, shadowHigh = 70800, shadowLow = 70000),
        CandleChartModel(open = 70200, close = 71000, shadowHigh = 71200, shadowLow = 70000),
        CandleChartModel(open = 71000, close = 72000, shadowHigh = 72500, shadowLow = 70800),
        CandleChartModel(open = 72000, close = 72500, shadowHigh = 72800, shadowLow = 71800),
        CandleChartModel(open = 72500, close = 73000, shadowHigh = 73500, shadowLow = 72000),
        CandleChartModel(open = 73000, close = 73500, shadowHigh = 74000, shadowLow = 72800),
        CandleChartModel(open = 73500, close = 73200, shadowHigh = 73800, shadowLow = 73000),
        CandleChartModel(open = 73200, close = 72800, shadowHigh = 73500, shadowLow = 72500),
        CandleChartModel(open = 72800, close = 72200, shadowHigh = 73000, shadowLow = 72000),
        CandleChartModel(open = 72200, close = 71500, shadowHigh = 72500, shadowLow = 71000),
        CandleChartModel(open = 71500, close = 71800, shadowHigh = 72000, shadowLow = 71200),
        CandleChartModel(open = 71800, close = 71000, shadowHigh = 72000, shadowLow = 70500),
        CandleChartModel(open = 71000, close = 70500, shadowHigh = 71200, shadowLow = 70000),
        CandleChartModel(open = 70500, close = 69800, shadowHigh = 70700, shadowLow = 69500),
        CandleChartModel(open = 69800, close = 69000, shadowHigh = 70000, shadowLow = 68500),
        CandleChartModel(open = 69000, close = 68800, shadowHigh = 69200, shadowLow = 68500),
        CandleChartModel(open = 68800, close = 68200, shadowHigh = 69000, shadowLow = 68000),
        CandleChartModel(open = 68200, close = 67800, shadowHigh = 68500, shadowLow = 67500),
        CandleChartModel(open = 67800, close = 67000, shadowHigh = 68000, shadowLow = 66800),
        CandleChartModel(open = 67000, close = 66000, shadowHigh = 67200, shadowLow = 65800),
        CandleChartModel(open = 66000, close = 65500, shadowHigh = 66200, shadowLow = 65200),
        CandleChartModel(open = 65500, close = 65000, shadowHigh = 65800, shadowLow = 64500),
        CandleChartModel(open = 65000, close = 64500, shadowHigh = 65200, shadowLow = 64000),
        CandleChartModel(open = 64500, close = 64000, shadowHigh = 64800, shadowLow = 63800),
        CandleChartModel(open = 64000, close = 64200, shadowHigh = 64500, shadowLow = 63800),
        CandleChartModel(open = 64200, close = 64800, shadowHigh = 65000, shadowLow = 64000),
        CandleChartModel(open = 64800, close = 65200, shadowHigh = 65500, shadowLow = 64500),
        CandleChartModel(open = 65200, close = 66000, shadowHigh = 66200, shadowLow = 65000),
        CandleChartModel(open = 66000, close = 65800, shadowHigh = 66300, shadowLow = 65500),
        CandleChartModel(open = 65800, close = 65500, shadowHigh = 66000, shadowLow = 65000),
        CandleChartModel(open = 65500, close = 64800, shadowHigh = 65700, shadowLow = 64500),
        CandleChartModel(open = 64800, close = 64000, shadowHigh = 65000, shadowLow = 63800)
    )

    val mockCommunity = listOf(
        CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은v지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        )
    )

    val mockNews = listOf(
        NewsModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            author = "이데일리",
            img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
        ),
        NewsModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            author = "이데일리",
            img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
        ),
        NewsModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            author = "이데일리",
            img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
        ),
        NewsModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            author = "이데일리",
            img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
        ),
        NewsModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            author = "이데일리",
            img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
        )
    )

    ChartScreen(
        chartInformation = ChartInformationModel(
            name = "마이크로 소프트",
            price = 218851,
            information = "마이크로소프트(Microsoft Corporation)는 미국의 다국적 기술 기업으로, 소프트웨어, 서비스, 디바이스 및 솔루션을 개발하고 지원합니다. 대표적인 제품으로는 Windows 운영 체제, Microsoft 365 생산성 응용 프로그램 제품군, Edge 웹 브라우저, Xbox 비디오 게임 콘솔, Surface 터치스크린 개인용 컴퓨터 등이 있습니다"
        ),
        candles = mockCandles,
        price = ChartPriceModel(
            dayMinPrice = 566772,
            dayMaxPrice = 600449,
            yearMinPrice = 426236,
            yearMaxPrice = 801212,
            dayOpen = 596772,
            dayClose = 598824,
            tradingVolume = 2912,
            tradingPrice = 23400000000
        ),
        news = mockNews,
        community = mockCommunity
    )
}