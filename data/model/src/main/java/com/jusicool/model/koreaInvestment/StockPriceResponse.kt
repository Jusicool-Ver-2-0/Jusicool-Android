import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockPriceResponse( // 주식현재가 시세 https://apiportal.koreainvestment.com/apiservice-apiservice?/uapi/domestic-stock/v1/quotations/inquire-price
    @Json(name = "rt_cd") val resultCode: String,
    @Json(name = "msg_cd") val messageCode: String,
    @Json(name = "msg1") val message: String,
    @Json(name = "output") val data: StockPriceData
)

@JsonClass(generateAdapter = true)
data class StockPriceData(
    @Json(name = "stck_prpr") val currentPrice: String,
    @Json(name = "prdy_vrss") val priceDifference: String,
    @Json(name = "prdy_vrss_sign") val priceDifferenceSign: String,
    @Json(name = "prdy_ctrt") val priceDifferenceRate: String,
    @Json(name = "acml_vol") val accumulatedVolume: String,
    @Json(name = "acml_tr_pbmn") val accumulatedTradeAmount: String,
    @Json(name = "stck_oprc") val openingPrice: String,
    @Json(name = "stck_hgpr") val highestPrice: String,
    @Json(name = "stck_lwpr") val lowestPrice: String,
    @Json(name = "stck_sdpr") val basePrice: String,
    @Json(name = "stck_mxpr") val upperLimitPrice: String,
    @Json(name = "stck_llam") val lowerLimitPrice: String,
    @Json(name = "hts_frgn_ehrt") val foreignRate: String,
    @Json(name = "frgn_ntby_qty") val foreignNetBuyQuantity: String,
    @Json(name = "pgtr_ntby_qty") val programNetBuyQuantity: String,
    @Json(name = "per") val per: String,
    @Json(name = "pbr") val pbr: String,
    @Json(name = "eps") val eps: String,
    @Json(name = "bps") val bps: String,
    @Json(name = "w52_hgpr") val week52High: String,
    @Json(name = "w52_lwpr") val week52Low: String,
    @Json(name = "vol_tnrt") val turnoverRate: String,
    @Json(name = "cpfn") val capital: String,
    @Json(name = "hts_avls") val marketCap: String,
    @Json(name = "lstn_stcn") val listedShares: String,
    @Json(name = "stck_fcam") val parValue: String
)
