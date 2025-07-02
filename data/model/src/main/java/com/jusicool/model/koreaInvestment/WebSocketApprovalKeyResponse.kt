package com.jusicool.model.koreaInvestment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebSocketAccessKeyResponse(
    @Json(name = "approval_key") val approvalKey: String
)