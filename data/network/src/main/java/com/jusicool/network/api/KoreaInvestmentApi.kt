package com.jusicool.network.api

import StockCandleResponse
import StockPriceResponse
import com.jusicool.model.koreaInvestment.AccessKeyRequest
import com.jusicool.model.koreaInvestment.AccessTokenResponse
import com.jusicool.model.koreaInvestment.StockMinutePriceResponse
import com.jusicool.model.koreaInvestment.WebSocketAccessKeyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface KoreaInvestmentApi {

    @POST("/oauth2/Approval")
    suspend fun getWebSocketAccessToken(
        @Body body: AccessKeyRequest,
    ): WebSocketAccessKeyResponse

    @POST("/oauth2/tokenP")
    suspend fun getAccessToken(
        @Body body: AccessKeyRequest,
    ): AccessTokenResponse


    @GET("/uapi/domestic-stock/v1/quotations/inquire-time-dailychartprice")
    suspend fun getStockOrder(
        @Header("appkey") appKey: String,
        @Header("appsecret") appSecret: String,
        @Header("tr_id") trId: String,
        @Header("tr_cont") trCont: String?,
        @Header("custtype") custType: String,

        @Query("FID_COND_MRKT_DIV_CODE") condMrktDivCode: String,
        @Query("FID_INPUT_ISCD") inputIsCd: String,
        @Query("FID_INPUT_HOUR_1") inputHour1: String,
        @Query("FID_INPUT_DATE_1") inputDate1: String,
        @Query("FID_PW_DATA_INCU_YN") pwDataIncuYn: String,
    ): StockCandleResponse

    @GET("/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice")
    suspend fun getMinutePrice(
        @Header("appkey") appKey: String,
        @Header("appsecret") appSecret: String,
        @Header("tr_id") trId: String,
        @Header("tr_cont") trCont: String?,
        @Header("custtype") custType: String,

        @Query("FID_COND_MRKT_DIV_CODE") condMrktDivCode: String,
        @Query("FID_INPUT_ISCD") inputIsCd: String,
        @Query("FID_INPUT_HOUR_1") inputHour1: String,
        @Query("FID_PW_DATA_INCU_YN") pwDataIncuYn: String,
        @Query("FID_ETC_CLS_CODE") etcClsCode: String
    ): StockMinutePriceResponse

    @GET("/uapi/domestic-stock/v1/quotations/inquire-price")
    suspend fun getStockCurrentPrice(
        @Header("appkey") appKey: String,
        @Header("appsecret") appSecret: String,
        @Header("tr_id") trId: String,
        @Header("tr_cont") trCont: String?,
        @Header("custtype") custType: String,

        @Query("FID_COND_MRKT_DIV_CODE") marketDivCode: String,
        @Query("FID_INPUT_ISCD") stockCode: String,
    ): StockPriceResponse
}