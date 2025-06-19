package com.jusicool.model.koreaInvestment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessKeyRequest(
    @Json(name = "grant_type") val grantType: String,
    @Json(name = "appkey") val appKey: String,
    @Json(name = "secretkey") val secretKey: String
)

@JsonClass(generateAdapter = true)
data class AccessTokenRequest(
    @Json(name = "grant_type") val grantType: String,
    @Json(name = "appkey") val appKey: String,
    @Json(name = "appsecret") val secretKey: String
)

