package com.jusicool.account.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.price.HoldingWithCurrentPrice
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AssetList(
    modifier: Modifier = Modifier,
    krwBalance: Long,
    getCurrentCryptoPriceData: PersistentList<HoldingWithCurrentPrice>,
    getCurrentStockPriceData: PersistentList<HoldingWithCurrentPrice>,
    navigateToChart: (marketCode: String, name: String, type: String, quantity: Int, money: Long, krwBalance: Long) -> Unit,
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "주식",
                color = colors.black,
                style = typography.bodySmall
            )

            getCurrentStockPriceData.forEach { stockData ->
                AssetListItem(
                    krwBalance = krwBalance,
                    currentCryptoPriceData = stockData,
                    navigateToChart = navigateToChart
                )            }

            Text(
                text = "코인",
                color = colors.black,
                style = typography.bodySmall
            )

            getCurrentCryptoPriceData.forEach { cryptoData ->
                AssetListItem(
                    krwBalance = krwBalance,
                    currentCryptoPriceData = cryptoData,
                    navigateToChart = navigateToChart
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssetListPreview() {
    AssetList(
        krwBalance = 1,
        getCurrentStockPriceData = persistentListOf(
            HoldingWithCurrentPrice(
                id = 1,
                market = Market(
                    1,
                    "삼성전자",
                    "Samsung Electronics",
                    "005930",
                    MarketType.STOCK
                ),
                purchasePrice = 50000,
                quantity = 2,
                currentPrice = 60000.0,
            ),
            HoldingWithCurrentPrice(
                id = 2,
                market = Market(2, "SK하이닉스", "SK Hynix", "000660", MarketType.STOCK),
                purchasePrice = 50000,
                quantity = 2,
                currentPrice = 60000.0,
            )
        ),
        getCurrentCryptoPriceData = persistentListOf(
            HoldingWithCurrentPrice(
                id = 1,
                market = Market(2, "비트코인", "bitcoin", "005930", MarketType.CRYPTO),
                purchasePrice = 50000,
                quantity = 12,
                currentPrice = 60000.0,
            ),
            HoldingWithCurrentPrice(
                id = 2,
                market = Market(3, "이더리움", "ethereum", "005930", MarketType.CRYPTO),
                purchasePrice = 50000,
                quantity = 12,
                currentPrice = 60000.0,
            )
        ),
        navigateToChart = { _, _, _, _, _, _ -> },
    )
}
