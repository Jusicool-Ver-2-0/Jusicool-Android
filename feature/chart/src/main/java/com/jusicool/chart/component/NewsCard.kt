package com.jusicool.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.news.NewsModel

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    news: List<NewsModel>
) {
    JusicoolTheme { colors, typography ->
        Column(modifier = modifier.fillMaxWidth()) {
            news.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .JusicoolClickable { /*TODO()*/ },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = item.title,
                            color = colors.black,
                            style = typography.bodyMedium,
                            maxLines = 2
                        )

                        Text(
                            text = item.author,
                            color = colors.gray400,
                            style = typography.label
                        )
                    }

                    AsyncImage(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        model = item.img,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
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

    NewsCard(
        news = mockNews
    )
}