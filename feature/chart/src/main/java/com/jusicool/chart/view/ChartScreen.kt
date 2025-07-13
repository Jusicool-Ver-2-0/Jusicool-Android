package com.jusicool.chart.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jusicool.chart.component.BuyBottomSheet
import com.jusicool.chart.component.CandleChart
import com.jusicool.chart.component.ChartPrice
import com.jusicool.chart.component.CommunityCard
import com.jusicool.chart.component.NewsCard
import com.jusicool.chart.component.PriceBarChart
import com.jusicool.chart.component.SellBottomSheet
import com.jusicool.chart.viewModel.CandleChartViewModel
import com.jusicool.chart.viewModel.uiState.GetCurrentMinuteCandleUiState
import com.jusicool.chart.viewModel.uiState.GetMinuteCandleUiState
import com.jusicool.design_system.component.button.JusicoolFilledButton
import com.jusicool.design_system.component.button.state.ButtonState
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.community.CommunityModel
import com.jusicool.model.news.NewsModel
import com.jusicool.design_system.icon.LeftClarityArrowLineIcon
import com.jusicool.design_system.icon.LetsIconsSettingFillIcon
import com.jusicool.design_system.icon.PencilIcon
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChartRoute(
    viewModel: CandleChartViewModel = hiltViewModel(),
    marketCode: String,
    koreanName: String,
    quantity: Int,
    money: Long,
    krwBalance: Long,
    type: String,
    navigateToBuy: (String, String, Long, Long, String) -> Unit,
    navigateToSell: (String, String, Int, String) -> Unit,
    navigateToReserveBuy: (String, String, Long, Long, String) -> Unit,
    navigateToReserveSell: (String, String, Int, String) -> Unit,
    navigateToCommunityPost: (String) -> Unit,
    popUpBackStack: () -> Unit
) {
    val minuteCandleUiState by viewModel.minuteCandleUiState.collectAsStateWithLifecycle()
    val currentMinuteCandleUiState by viewModel.getCurrentMinuteCandleUiState.collectAsStateWithLifecycle()

    val now = LocalDateTime.now()
    val oneMinuteLater = now.plusMinutes(-1)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val formattedNow = oneMinuteLater.format(formatter)

    val refreshCandleData = viewModel::refreshCandleData

    LaunchedEffect(Unit) {
        viewModel.getMinuteCandle(market = marketCode, to = formattedNow, count = 200)
        viewModel.getMarkets(market = marketCode)
        viewModel.startPeriodicRequest(market = marketCode)
    }


    val mockCommunity = listOf(
        CommunityModel(
            title = "AK홀딩스 이번에 물류 시스템 바꿨다던데",
            content = "지인 회사에서 AK 물류랑 일하는데 AI 시스템으로 바뀌고 나서 물류 처리 속도 확 달라졌다고 함. 괜히 지주회사가 아님 ㄷㄷ",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "AK홀딩스 ESG 진짜 하는 듯",
            content = "요즘 애경유화 쪽에서 폐플라스틱 재활용 엄청 밀고 있음. 그냥 보여주기식인 줄 알았는데 진짜 공장 돌리고 있다더라.",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "AK홀딩스 주가 언제쯤 움직일까?",
            content = "요즘 뉴스 보면 이것저것 하는 거 많은데 주가는 왜 이 모양일까... 자사주 매입도 했다면서요? 믿고 들고 있어도 되나?",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "AK홀딩스 애경산업 요즘 중국에서 인기 많다던데",
            content = "AGE 20’s 요즘 왕홍들이 엄청 밀어주고 있음ㅋㅋㅋ K-뷰티 다시 흥하나 싶다. 이거 때문에 애경 실적 오르면 AK도 같이 올라갈 듯?",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "제주항공 유럽 노선 탄다고? ㄷㄷ",
            content = "AK홀딩스가 제주항공에 투자 엄청 한 듯. 유럽 신규 노선 만든다고 하던데… LCC가 유럽 가는 거면 진짜 파격이다ㅋㅋ",
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
            name = "AK 홀딩스",
            price = 218851,
            information = "AK홀딩스는 애경그룹의 지주회사로서, 애경산업(생활용품), 애경유화(화학), 제주항공(항공) 등 주요 계열사를 관리하며 그룹의 경영 전략을 총괄하는 역할을 한다. 다양한 산업에 걸쳐 투자 포트폴리오를 구성해 안정적인 수익 구조를 추구하고 있으며, 최근에는 바이오, 헬스케어 등 신사업 영역으로의 확장도 모색하고 있다."
        ),
        minuteCandleData = minuteCandleUiState,
        currentMinuteCandleData = currentMinuteCandleUiState,
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
        community = mockCommunity,
        popUpBackStack = popUpBackStack,
        koreanName = koreanName,
        marketCode = marketCode,
        quantity = quantity,
        money = money,
        krwBalance = krwBalance,
        type = type,
        onRefresh = refreshCandleData,
        navigateToBuy = navigateToBuy,
        navigateToSell = navigateToSell,
        navigateToReserveBuy = navigateToReserveBuy,
        navigateToReserveSell = navigateToReserveSell,
        navigateToCommunityPost = navigateToCommunityPost
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(
    modifier: Modifier = Modifier,
    koreanName: String,
    marketCode: String,
    quantity: Int,
    money: Long,
    krwBalance: Long,
    type: String,
    minuteCandleData: GetMinuteCandleUiState,
    currentMinuteCandleData: GetCurrentMinuteCandleUiState,
    chartInformation: ChartInformationModel,
    price: ChartPriceModel,
    news: List<NewsModel>,
    community: List<CommunityModel>,
    navigateToBuy: (String, String, Long, Long, String) -> Unit,
    navigateToSell: (String, String, Int, String) -> Unit,
    navigateToReserveBuy: (String, String, Long, Long, String) -> Unit,
    navigateToReserveSell: (String, String, Int, String) -> Unit,
    onRefresh: (String) -> Unit,
    navigateToCommunityPost: (String) -> Unit,
    popUpBackStack: () -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedInfo by remember { mutableStateOf("종목 정보") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var showBuyBottomSheet by remember { mutableStateOf(false) }
    var showSellBottomSheet by remember { mutableStateOf(false) }

    JusicoolTheme { colors, typography ->
        if (showBuyBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBuyBottomSheet = false },
                sheetState = sheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() },
                containerColor = colors.white,
            ) {
                BuyBottomSheet(
                    name = koreanName,
                    money = money,
                    krwBalance = krwBalance,
                    type = type,
                    navigateToBuy = navigateToBuy,
                    marketCode = marketCode,
                    navigateToReserveBuy = navigateToReserveBuy
                )
            }
        }

        if (showSellBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSellBottomSheet = false },
                sheetState = sheetState,
                dragHandle = { BottomSheetDefaults.DragHandle() },
                containerColor = colors.white,
            ) {
                SellBottomSheet(
                    name = koreanName,
                    quantity = quantity,
                    type = type,
                    marketCode = marketCode,
                    navigateToSell = navigateToSell,
                    navigateToReserveSell = navigateToReserveSell
                )
            }
        }


        Scaffold(
            modifier = modifier.fillMaxSize(),
            content = { paddingValues ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(color = colors.white)
                        .padding(paddingValues)
                ) {
                    JusicoolTopBar(
                        modifier = Modifier.fillMaxWidth(),
                        betweenText = koreanName,
                        startIcon = { LeftClarityArrowLineIcon(modifier = Modifier.JusicoolClickable { popUpBackStack() }) },
                        endIcon = { LetsIconsSettingFillIcon(modifier = Modifier.JusicoolClickable { /*TODO()*/ }) }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ChartPrice(currentMinuteCandleData = currentMinuteCandleData)

                        val candles = when (minuteCandleData) {
                            is GetMinuteCandleUiState.Success -> minuteCandleData.chart
                            else -> emptyList()
                        }

                        CandleChart(
                            modifier = Modifier.fillMaxWidth(),
                            candles = candles,
                            currentCandlesData = currentMinuteCandleData,
                            market = marketCode,
                            onRefresh = { onRefresh(marketCode) }
                        )

                        if (quantity < 1) {
                            JusicoolFilledButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                text = "구매하기",
                                state = ButtonState.Enable,
                                filledColor = colors.error,
                                onClick = {
                                    coroutineScope.launch {
                                        showBuyBottomSheet = true
                                        sheetState.show()
                                    }
                                }
                            )
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                JusicoolFilledButton(
                                    modifier = Modifier.weight(1f),
                                    text = "구매하기",
                                    state = ButtonState.Enable,
                                    filledColor = colors.error,
                                    onClick = {
                                        coroutineScope.launch {
                                            showBuyBottomSheet = true
                                            sheetState.show()
                                        }
                                    }
                                )

                                JusicoolFilledButton(
                                    modifier = Modifier.weight(1f),
                                    text = "판매하기",
                                    state = ButtonState.Enable,
                                    filledColor = colors.main,
                                    onClick = {
                                        coroutineScope.launch {
                                            showSellBottomSheet = true
                                            sheetState.show()
                                        }
                                    }
                                )
                            }
                        }

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
                                        horizontalAlignment = Alignment.CenterHorizontally,
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
                                                    .background(
                                                        color = colors.gray100,
                                                        shape = RoundedCornerShape(size = 1.dp)
                                                    )
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

                                else -> {}
                            }
                        }
                    }
                }
            },
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = colors.main,
                            shape = RoundedCornerShape(size = 18.dp)
                        )
                        .padding(12.dp)
                ) {
                    PencilIcon(
                        modifier = Modifier
                            .size(24.dp)
                            .JusicoolClickable { navigateToCommunityPost(marketCode) }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChartScreenPreview() {
    ChartScreen(
        koreanName = "AK홀딩스",
        chartInformation = ChartInformationModel(
            name = "AK홀딩스",
            price = 218_851,
            information = "AK홀딩스는 애경그룹의 지주회사로서, 애경산업(생활용품), 애경유화(화학), 제주항공(항공) 등 주요 계열사를 관리하며 그룹의 경영 전략을 총괄하는 역할을 한다. 다양한 산업에 걸쳐 투자 포트폴리오를 구성해 안정적인 수익 구조를 추구하고 있으며, 최근에는 바이오, 헬스케어 등 신사업 영역으로의 확장도 모색하고 있다."
        ),
        minuteCandleData = GetMinuteCandleUiState.Success(emptyList()), // 또는 적절한 mock 데이터
        currentMinuteCandleData = GetCurrentMinuteCandleUiState.Blank,
        price = ChartPriceModel(
            dayMinPrice = 566_772,
            dayMaxPrice = 600_449,
            yearMinPrice = 426_236,
            yearMaxPrice = 801_212,
            dayOpen = 596_772,
            dayClose = 598_824,
            tradingVolume = 2_912,
            tradingPrice = 23_400_000_000
        ),
        news = listOf(
            NewsModel(
                title = "세계경제 2.6% 성장 전망",
                author = "이데일리",
                img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
            )
        ),
        community = listOf(
            CommunityModel(
                title = "AK홀딩스 이번에 물류 시스템 바꿨다던데",
                content = "지인 회사에서 AK 물류랑 일하는데 AI 시스템으로 바뀌고 나서 물류 처리 속도 확 달라졌다고 함. 괜히 지주회사가 아님 ㄷㄷ",
                author = "이데일리",
                like = 24,
                day = "06.20일 17:06",
                comment = 24
            )
        ),
        popUpBackStack = {},
        marketCode = "",
        quantity = 0,
        money = 1,
        type = "",
        krwBalance = 1,
        onRefresh = {},
        navigateToBuy = { _, _, _, _, _ -> },
        navigateToSell = { _, _, _, _ -> },
        navigateToReserveBuy = { _, _, _, _, _ -> },
        navigateToReserveSell = { _, _, _, _ -> },
        navigateToCommunityPost = {}
    )
}


data class ChartPriceModel(
    val dayMinPrice: Int,
    val dayMaxPrice: Int,
    val yearMinPrice: Int,
    val yearMaxPrice: Int,
    val dayOpen: Int,
    val dayClose: Int,
    val tradingVolume: Int,
    val tradingPrice: Long
)

data class ChartInformationModel(
    val name: String,
    val price: Int,
    val information: String
)
