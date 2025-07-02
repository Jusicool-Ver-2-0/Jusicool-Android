package com.jusicool.model.koreaInvestment.ws

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DomesticStockRealtimeResponse(
    @Json(name = "header")
    val header: RealtimeResponseHeader,

    @Json(name = "body")
    val body: RealtimeResponseBody
)

@JsonClass(generateAdapter = true)
data class RealtimeResponseHeader(
    @Json(name = "tr_id")
    val transactionId: String,

    @Json(name = "tr_key")
    val transactionKey: String,

    @Json(name = "encrypt")
    val encryptionFlag: String
)

@JsonClass(generateAdapter = true)
data class RealtimeResponseBody(
    @Json(name = "rt_cd")
    val resultCode: String,

    @Json(name = "msg_cd")
    val messageCode: String,

    @Json(name = "msg1")
    val message: String,

    @Json(name = "output")
    val outputData: RealtimeOutputData?
)

@JsonClass(generateAdapter = true)
data class RealtimeOutputData(
    @Json(name = "iv")
    val aesIv: String,

    @Json(name = "key")
    val aesKey: String
)
