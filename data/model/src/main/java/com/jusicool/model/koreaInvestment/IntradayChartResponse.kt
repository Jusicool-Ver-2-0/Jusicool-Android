import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockCandleResponse( // 주식일별분봉조회 https://apiportal.koreainvestment.com/apiservice-apiservice?/uapi/domestic-stock/v1/quotations/inquire-time-dailychartprice
    @Json(name = "rt_cd") val resultCode: String,
    @Json(name = "msg_cd") val messageCode: String,
    @Json(name = "msg1") val message: String,
    @Json(name = "output1") val summary: StockSummary,
    @Json(name = "output2") val candles: List<StockMinuteCandle> = emptyList()
)

@JsonClass(generateAdapter = true)
data class StockSummary(
    @Json(name = "prdy_vrss") val previousDayDifference: String,
    @Json(name = "prdy_vrss_sign") val differenceSign: String,
    @Json(name = "prdy_ctrt") val differenceRate: String,
    @Json(name = "stck_prdy_clpr") val previousClosingPrice: String,
    @Json(name = "acml_vol") val accumulatedVolume: String,
    @Json(name = "acml_tr_pbmn") val accumulatedTradeAmount: String,
    @Json(name = "hts_kor_isnm") val stockNameKorean: String,
    @Json(name = "stck_prpr") val currentPrice: String
)

@JsonClass(generateAdapter = true)
data class StockMinuteCandle(
    @Json(name = "stck_bsop_date") val date: String,
    @Json(name = "stck_cntg_hour") val time: String,
    @Json(name = "stck_prpr") val currentPrice: String,
    @Json(name = "stck_oprc") val openingPrice: String,
    @Json(name = "stck_hgpr") val highPrice: String,
    @Json(name = "stck_lwpr") val lowPrice: String,
    @Json(name = "cntg_vol") val transactionVolume: String,
    @Json(name = "acml_tr_pbmn") val accumulatedTradeAmount: String
)
