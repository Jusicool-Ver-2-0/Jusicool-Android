package com.jusicool.model.account

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccountResponse(
    val id: String,
    @Json(name = "krw_balance") val krwBalance: Long
)