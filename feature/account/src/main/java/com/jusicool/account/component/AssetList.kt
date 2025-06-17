package com.jusicool.account.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.account.viewModel.uiState.GetCurrentCryptoPriceUiState
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.holding.HoldingModel
import com.jusicool.usecase.crypto.CurrentCryptoHoldingPrice
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AssetList(
    modifier: Modifier = Modifier,
    krwBalance: Long,
    holdings: PersistentList<HoldingModel>,
    getCurrentCryptoPriceData: GetCurrentCryptoPriceUiState,
    navigateToChart: (marketCode: String, name: String, quantity: Int, money: Long) -> Unit,
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (holdings.any { it.marketType == "STOCK" }) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "주식",
                        color = colors.black,
                        style = typography.bodySmall
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        holdings.filter { it.marketType == "STOCK" }.forEach { asset ->
                            StockAssetListItem(
                                holding = asset
                            )
                        }
                    }
                }
            }


            if (holdings.any { it.marketType == "CRYPTO" }) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "코인",
                        color = colors.black,
                        style = typography.bodySmall
                    )


                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        holdings.filter { it.marketType == "CRYPTO" }.forEach { holding ->
                            CryptoAssetListItem(
                                holding = holding,
                                krwBalance = krwBalance,
                                getCurrentCryptoPriceData = getCurrentCryptoPriceData,
                                navigateToChart = navigateToChart
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetListPreview() {
    AssetList(
        holdings = persistentListOf(
            HoldingModel(1, 1, "삼성전자", "Samsung", "005930.KQ", "STOCK", 10, 70000),
            HoldingModel(2, 2, "비트코인", "Bitcoin", "BTC", "CRYPTO", 2, 55000000)
        ),
        getCurrentCryptoPriceData = GetCurrentCryptoPriceUiState.Success(
            markets = listOf(
                CurrentCryptoHoldingPrice(
                    marketCode = "weqwe",
                    currentPrice = 12.00,
                    priceVariation= 12,
                    priceVariationPercent= 12.00,
                    totalVariation = 1,
                    totalValue = 1
                )
            )
        ),
        krwBalance = 1,
        navigateToChart = { marketCode, name, quantity, money -> }
    )
}
