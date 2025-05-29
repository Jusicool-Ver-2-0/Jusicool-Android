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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.asset.AssetType
import com.jusicool.model.asset.UserStockCryptoModel

@Composable
fun UserAssetCard(
    modifier: Modifier = Modifier,
    userStockCryptoModel : UserStockCryptoModel
) {
    JusicoolTheme { colors, typography ->
        val textColor = when {
            userStockCryptoModel.priceVariation < 0 -> colors.main
            userStockCryptoModel.priceVariation > 0 -> colors.error
            else -> colors.gray400
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
                ,verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(width = 1.dp, color = colors.gray100, shape = RoundedCornerShape(size = 20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        model = userStockCryptoModel.logoUrl,
                        contentDescription = null,
                    )
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = userStockCryptoModel.name,
                        color = colors.black,
                        style = typography.bodySmall
                    )

                    Text(
                        text = "${"%,d".format(userStockCryptoModel.amount)}주",
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
                Text(
                    text = "${"%,d".format(userStockCryptoModel.price)}원",
                    color = colors.black,
                    style = typography.bodySmall
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = if (userStockCryptoModel.priceVariation >= 0) {
                            "+%,d".format(userStockCryptoModel.priceVariation)
                        } else {
                            "%,d".format(userStockCryptoModel.priceVariation)
                        },
                        color = textColor,
                        style = typography.label
                    )

                    Text(
                        text = "(${userStockCryptoModel.priceVariationPercent}%)",
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
fun UserAssetCardPreview() {
    UserAssetCard(
        userStockCryptoModel = UserStockCryptoModel(
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/625px-Apple_logo_black.svg.png",
            type = AssetType.STOCK,
            name = "애플",
            amount = 123,
            price = 11111111,
            priceVariation = -1111111,
            priceVariationPercent = 4.0
        )
    )
}