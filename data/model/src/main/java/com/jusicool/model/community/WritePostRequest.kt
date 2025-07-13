package com.jusicool.model.community

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WritePostRequest(
    val title: String,
    val content: String
)