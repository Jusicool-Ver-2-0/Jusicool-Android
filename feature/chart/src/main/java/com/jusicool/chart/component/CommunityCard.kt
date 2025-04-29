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
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.model.community.CommunityModel
import com.school_of_company.design_system.icon.CommentIcon
import com.school_of_company.design_system.icon.HeartIcon

@Composable
fun CommunityCard(
    modifier: Modifier = Modifier,
    community: List<CommunityModel>
) {
    JusicoolTheme { colors, typography ->
        Column(modifier = modifier.fillMaxWidth()) {
            community.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .JusicoolClickable { /*TODO()*/ },
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = item.title,
                            color = colors.black,
                            style = typography.subTitle,
                            maxLines = 1
                        )

                        Text(
                            text = item.content,
                            color = colors.gray600,
                            style = typography.bodySmall,
                            maxLines = 2
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.author} | ${item.day}",
                            color = colors.gray400,
                            style = typography.label
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                HeartIcon(modifier = Modifier.size(18.dp))

                                Text(
                                    text = "${item.like}",
                                    color = colors.gray400,
                                    style = typography.label
                                )
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CommentIcon()

                                Text(
                                    text = "${item.comment}",
                                    color = colors.gray400,
                                    style = typography.label
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityCardPreview() {
    val mockCommunity = listOf(
        CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은커뮤니티는공통의관심사목표가치혹은v지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),
        CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        ),CommunityModel(
            title = "세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망세계경제 2.6% 성장 전망",
            content = "커뮤니티는공통의관심사목표가치혹은지리적위치를공유하는사람들로이루어진집단입니다이러한집단은개인",
            author = "이데일리",
            like = 24,
            day = "06.20일 17:06",
            comment = 24
        )


    )

    CommunityCard(
        community = mockCommunity
    )
}