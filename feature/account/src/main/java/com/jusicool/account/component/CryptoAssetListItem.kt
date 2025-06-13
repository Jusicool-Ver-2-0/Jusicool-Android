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
import com.jusicool.account.viewModel.uiState.GetCurrentCryptoPriceUiState
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.entity.crypto.CurrentCryptoPriceModel
import com.jusicool.entity.holding.HoldingModel
import com.jusicool.usecase.crypto.CurrentCryptoHoldingPrice
import com.jusicool.utils.formatMoney
import com.jusicool.utils.formatPercent
import com.jusicool.utils.toSignedFormattedText

@Composable
fun CryptoAssetListItem(
    modifier: Modifier = Modifier,
    holding: HoldingModel,
    getCurrentCryptoPriceData: GetCurrentCryptoPriceUiState
) {
    JusicoolTheme { colors, typography ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
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
                        .border(width = 1.dp, color = colors.gray100, shape = RoundedCornerShape(size = 20.dp)),
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
                        text = holding.koreanName,
                        color = colors.black,
                        style = typography.bodySmall
                    )

                    Text(
                        text = "${holding.quantity.formatMoney()}주",
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
                when(getCurrentCryptoPriceData) {
                    is GetCurrentCryptoPriceUiState.Success -> {
                        val priceInfo = getCurrentCryptoPriceData.markets.find {
                            it.marketCode == holding.marketCode
                        }

                        if (priceInfo != null) {
                            val textColor =
                                if (priceInfo.totalVariation >= 0) colors.chartPriceIncreased else colors.chartPriceDecreased

                            Text(
                                text = "${priceInfo.totalValue.formatMoney()}원",
                                color = colors.black,
                                style = typography.bodySmall
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = priceInfo.totalVariation.toSignedFormattedText(),
                                    color = textColor,
                                    style = typography.label
                                )

                                Text(
                                    text = "(${priceInfo.priceVariationPercent.formatPercent()})",
                                    color = textColor,
                                    style = typography.label
                                )
                            }
                        }
                    }

                    is GetCurrentCryptoPriceUiState.Error -> {

                    }
                    is GetCurrentCryptoPriceUiState.Loading -> {

                    }
                    is GetCurrentCryptoPriceUiState.Blank -> {

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CryptoAssetListItemPreview() {
    CryptoAssetListItem(
        holding = HoldingModel(
            id = 1,
            marketId = 101,
            koreanName = "삼성전자",
            englishName = "Samsung Electronics",
            marketCode = "005930.KQ",
            marketType = "STOCK",
            quantity = 15,
            price = 75000
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
        )
    )
}
