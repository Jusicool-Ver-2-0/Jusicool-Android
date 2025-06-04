package com.jusicool.model.koreaInvestment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockMinutePriceResponse( // 주식당일분봉조회
    @Json(name = "rt_cd")
    val rtCode: String,

    @Json(name = "msg_cd")
    val msgCode: String,

    @Json(name = "msg1")
    val message: String,

    @Json(name = "output1")
    val summary: StockSummary,

    @Json(name = "output2")
    val details: List<StockMinuteDetail>
)

@JsonClass(generateAdapter = true)
data class StockSummary(
    @Json(name = "prdy_vrss")
    val previousDifference: String,

    @Json(name = "prdy_vrss_sign")
    val previousDifferenceSign: String,

    @Json(name = "prdy_ctrt")
    val previousChangeRate: String,

    @Json(name = "stck_prdy_clpr")
    val previousClosePrice: String,

    @Json(name = "acml_vol")
    val accumulatedVolume: String,

    @Json(name = "acml_tr_pbmn")
    val accumulatedTradePrice: String,

    @Json(name = "hts_kor_isnm")
    val stockName: String,

    @Json(name = "stck_prpr")
    val currentPrice: String
)

@JsonClass(generateAdapter = true)
data class StockMinuteDetail(
    @Json(name = "stck_bsop_date")
    val baseDate: String,

    @Json(name = "stck_cntg_hour")
    val baseHour: String,

    @Json(name = "stck_prpr")
    val currentPrice: String,

    @Json(name = "stck_oprc")
    val openPrice: String,

    @Json(name = "stck_hgpr")
    val highPrice: String,

    @Json(name = "stck_lwpr")
    val lowPrice: String,

    @Json(name = "cntg_vol")
    val volume: String,

    @Json(name = "acml_tr_pbmn")
    val accumulatedTradePrice: String
)
