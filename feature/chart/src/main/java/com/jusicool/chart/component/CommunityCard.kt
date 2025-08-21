package com.jusicool.chart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jusicool.design_system.component.modifier.JusicoolClickable
import com.jusicool.design_system.theme.JusicoolTheme
import com.jusicool.design_system.icon.CommentIcon
import com.jusicool.design_system.icon.HeartIcon
import com.jusicool.entity.community.CommunityListModel

@Composable
fun CommunityCard(
    modifier: Modifier = Modifier,
    communityList: List<CommunityListModel>
) {
    JusicoolTheme { colors, typography ->
        Column(modifier = modifier.fillMaxWidth()) {
            communityList.forEach { item ->
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
                                    text = "${item.likeCount}",
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
                                    text = "${item.commentCount}",
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