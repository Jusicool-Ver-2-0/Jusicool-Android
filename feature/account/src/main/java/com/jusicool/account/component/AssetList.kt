package com.jusicool.account.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.account.viewModel.uiState.GetCurrentCryptoPriceUiState
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.entity.holding.HoldingModel

@Composable
fun AssetList(
    modifier: Modifier = Modifier,
    holdings: List<HoldingModel>,
    getCurrentCryptoPriceData: GetCurrentCryptoPriceUiState
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val stockHoldings = holdings.filter { it.marketType == "STOCK" }
            if (stockHoldings.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "주식",
                        color = colors.black,
                        style = typography.bodySmall
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        stockHoldings.forEach { asset ->
                            StockAssetListItem(
                                holding = asset
                            )
                        }
                    }
                }
            }


            val cryptoHoldings = holdings.filter { it.marketType == "CRYPTO" }

            if (cryptoHoldings.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "코인",
                        color = colors.black,
                        style = typography.bodySmall
                    )


                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        cryptoHoldings.forEach { holding ->
                            CryptoAssetListItem(
                                holding = holding,
                                getCurrentCryptoPriceData = getCurrentCryptoPriceData
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
        holdings = listOf(
            HoldingModel(1, 1, "삼성전자", "Samsung", "005930.KQ", "STOCK", 10, 70000),
            HoldingModel(2, 2, "비트코인", "Bitcoin", "BTC", "CRYPTO", 2, 55000000)
        ),
        getCurrentCryptoPriceData = GetCurrentCryptoPriceUiState.Success(
            markets = listOf(CurrentCryptoPriceModel(tradePrice = 10000.0, market = "BTC"))
        )
    )
}
