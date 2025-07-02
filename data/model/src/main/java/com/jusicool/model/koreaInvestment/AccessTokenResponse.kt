package com.jusicool.model.koreaInvestment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "token_type") val tokenType: String,
    @Json(name = "expires_in") val expiresIn: Double,
    @Json(name = "access_token_token_expired") val tokenExpiryDateTime: String
)
