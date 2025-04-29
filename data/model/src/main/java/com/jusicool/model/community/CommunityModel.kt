package com.jusicool.model.community

data class CommunityModel(
    val title: String,
    val content: String,
    val author: String,
    val day: String,
    val like: Int,
    val comment: Int
)