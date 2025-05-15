package com.jusicool.account.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jusicool.account.component.HoldingNewsCard
import com.jusicool.account.component.UserAssetCard
import com.jusicool.design_system.R
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.component.topbar.JusicoolTopBar
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.asset.AssetType
import com.jusicool.model.asset.UserAssetModel
import com.jusicool.model.asset.UserStockCryptoModel
import com.jusicool.model.news.HoldingNewsModel
import com.school_of_company.design_system.icon.RightArrowIcon

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    userAssetModel: UserAssetModel,
    userStockCryptoModel: List<UserStockCryptoModel>,
    holdingNewsModel: HoldingNewsModel
) {
    val scrollState = rememberScrollState()

    JusicoolTheme { colors, typography ->
        Column(modifier = modifier
            .fillMaxSize()
            .background(colors.white)
        ) {
            JusicoolTopBar(
                startIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.union),
                        contentDescription = "Jusicool Logo",
                        modifier = Modifier
                            .width(116.dp)
                            .height(16.dp)
                    )
                },
                endIcon = {}
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            modifier = Modifier.JusicoolClickable { /*TODO()*/ },
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "내 자산",
                                color = colors.black,
                                style = typography.bodyMedium
                            )
                            RightArrowIcon()
                        }

                        Text(
                            text = "${"%,d".format(userAssetModel.myAsset)}원",
                            color = colors.black,
                            style = typography.titleSmall
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "투자 자산",
                            color = colors.black,
                            style = typography.bodyMedium
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "${"%,d".format(userAssetModel.investAsset)}원",
                                color = colors.black,
                                style = typography.titleMedium
                            )

                            Text(
                                text = "-6,555,555원 (4.0%)",
                                color = colors.main,
                                style = typography.bodySmall
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "보유 주식&코인",
                                color = colors.black,
                                style = typography.subTitle
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "주식",
                                        color = colors.black,
                                        style = typography.bodySmall
                                    )

                                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                        userStockCryptoModel.filter { it.type == AssetType.STOCK }
                                            .forEach { asset ->
                                                UserAssetCard(userStockCryptoModel = asset)
                                            }
                                    }
                                }

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "코인",
                                        color = colors.black,
                                        style = typography.bodySmall
                                    )

                                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                        userStockCryptoModel.filter { it.type == AssetType.CRYPTO }
                                            .forEach { asset ->
                                                UserAssetCard(userStockCryptoModel = asset)
                                            }
                                    }
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .background(colors.gray100)
                                .fillMaxWidth()
                                .height(1.dp)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "주문 내역",
                                    color = colors.black,
                                    style = typography.bodySmall
                                )

                                Row(
                                    modifier = Modifier.JusicoolClickable { /*TODO()*/ },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "이번 달 ${userAssetModel.orderHistory}건",
                                        color = colors.gray600,
                                        style = typography.bodySmall
                                    )

                                    RightArrowIcon(
                                        modifier = Modifier.size(24.dp),
                                        tint = colors.gray400
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "이번 달 수익",
                                    color = colors.black,
                                    style = typography.bodySmall
                                )

                                Row(
                                    modifier = Modifier.JusicoolClickable { /*TODO()*/ },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (userAssetModel.monthProfit >= 0) {
                                            "+%,d".format(userAssetModel.monthProfit)
                                        } else {
                                            "%,d".format(userAssetModel.monthProfit)
                                        },
                                        color = colors.gray600,
                                        style = typography.bodySmall
                                    )

                                    RightArrowIcon(
                                        modifier = Modifier.size(24.dp),
                                        tint = colors.gray400
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(colors.gray50)
                )

                HoldingNewsCard(holdingNewsModel = holdingNewsModel)
            }
        }
    }
}

@Composable
fun AccountScreenPreview() {
    val mockUserAssetModel = UserAssetModel(
        myAsset = 50000000,
        investAsset = 1000000000,
        orderHistory = 10,
        monthProfit = 111111111
    )

    val mockUserStockCryptoModel = listOf(
        UserStockCryptoModel(
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/625px-Apple_logo_black.svg.png",
            type = AssetType.STOCK,
            name = "애플",
            amount = 123,
            price = 11111111,
            priceVariation = -1111111,
            priceVariationPercent = 4.0
        ),
        UserStockCryptoModel(
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1280px-Bitcoin.svg.png",
            type = AssetType.CRYPTO,
            name = "비트코인",
            amount = 10,
            price = 50000000,
            priceVariation = 1000000,
            priceVariationPercent = 2.0
        ),
        UserStockCryptoModel(
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1280px-Bitcoin.svg.png",
            type = AssetType.CRYPTO,
            name = "비트코인",
            amount = 10,
            price = 50000000,
            priceVariation = 1000000,
            priceVariationPercent = 2.0
        ),
        UserStockCryptoModel(
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1280px-Bitcoin.svg.png",
            type = AssetType.CRYPTO,
            name = "비트코인",
            amount = 10,
            price = 50000000,
            priceVariation = 0,
            priceVariationPercent = 0.0
        ),
        UserStockCryptoModel(
            logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/1024px-Google_%22G%22_logo.svg.png",
            type = AssetType.STOCK,
            name = "구글",
            amount = 50,
            price = 2500000,
            priceVariation = 50000,
            priceVariationPercent = 2.1
        )
    )

    val mockHoldingNewsModel = HoldingNewsModel(
        author = "이데일리",
        title = "애플, 사상 최고가... 올해 세계경제 2.6% 성장 전망",
        img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
    )

    AccountScreen(
        userAssetModel = mockUserAssetModel,
        userStockCryptoModel = mockUserStockCryptoModel,
        holdingNewsModel = mockHoldingNewsModel
    )
}