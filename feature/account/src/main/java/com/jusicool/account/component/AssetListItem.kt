package com.jusicool.account.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.market.Market
import com.jusicool.entity.market.MarketType
import com.jusicool.entity.price.HoldingWithCurrentPrice
import com.jusicool.utils.formatMoney
import com.jusicool.utils.formatPercent
import com.jusicool.utils.toSignedFormattedText

@Composable
fun AssetListItem(
    modifier: Modifier = Modifier,
    krwBalance: Long,
    currentCryptoPriceData: HoldingWithCurrentPrice,
    navigateToChart: (marketCode: String, name: String, type: String, quantity: Int, money: Long, krwBalance: Long) -> Unit
) {
    JusicoolTheme { colors, typography ->

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .JusicoolClickable {
                    navigateToChart(
                        currentCryptoPriceData.market.market,
                        currentCryptoPriceData.market.koreanName,
                        currentCryptoPriceData.market.marketType.name,
                        currentCryptoPriceData.quantity,
                        currentCryptoPriceData.currentPrice.toLong(),
                        krwBalance
                    )
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            width = 1.dp,
                            color = colors.gray100,
                            shape = RoundedCornerShape(size = 20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        model = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg",
                        contentDescription = null,
                    )
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentCryptoPriceData.market.koreanName,
                        color = colors.black,
                        style = typography.bodySmall
                    )

                    Text(
                        text = "${currentCryptoPriceData.quantity.formatMoney()}주",
                        color = colors.gray400,
                        style = typography.label
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                val textColor =
                    if (currentCryptoPriceData.totalVariation() >= 0) colors.chartPriceIncreased
                    else colors.chartPriceDecreased

                Text(
                    text = "${currentCryptoPriceData.totalValue().formatMoney()}원",
                    color = colors.black,
                    style = typography.bodySmall
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = currentCryptoPriceData.totalVariation().toSignedFormattedText(),
                        color = textColor,
                        style = typography.label
                    )

                    Text(
                        text = "(${
                            currentCryptoPriceData.priceVariationPercent().formatPercent()
                        })",
                        color = textColor,
                        style = typography.label
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CryptoAssetListItemPreview() {
    AssetListItem(
        currentCryptoPriceData =
        HoldingWithCurrentPrice(
            id = 2,
            market = Market(
                market = "102",
                koreanName = "비트코인",
                englishName = "Bitcoin",
                marketType = MarketType.CRYPTO,
                id = 2
            ),
            quantity = 5,
            purchasePrice = 25000,
            currentPrice = 30000.0
        ),
        krwBalance = 1,
        navigateToChart = { marketCode, name, type, quantity, money, krwBalance -> },
    )
}
