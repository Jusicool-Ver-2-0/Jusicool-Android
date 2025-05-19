package com.jusicool.account.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.news.HoldingNewsModel

@Composable
fun HoldingNewsCard(
    modifier: Modifier = Modifier,
    holdingNewsModel: HoldingNewsModel
) {
    JusicoolTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "보유 종목 뉴스",
                color = colors.black,
                style = typography.subTitle
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(156.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    model = holdingNewsModel.img,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = holdingNewsModel.title,
                    color = colors.black,
                    style = typography.bodyMedium,
                    maxLines = 2
                )

                Text(
                    text = holdingNewsModel.author,
                    color = colors.gray400,
                    style = typography.label
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HoldingNewsCardPreview() {
    HoldingNewsCard(
        holdingNewsModel = HoldingNewsModel(
            author = "이데일리",
            title = "애플, 사상 최고가... 올해 세계경제 2.6% 성장 전망",
            img = "https://i.pinimg.com/474x/3d/c9/64/3dc9647bffee1578c683db59d9cbaa24.jpg"
        )
    )
}