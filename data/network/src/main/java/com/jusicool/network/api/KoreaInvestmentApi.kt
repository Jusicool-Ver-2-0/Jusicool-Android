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
        @Header("content-type") contentType: String,
        @Header("authorization") authorization: String,
        @Header("appkey") appKey: String,
        @Header("appsecret") appSecret: String,
        @Header("personalseckey") personalSecKey: String? = null,
        @Header("tr_id") trId: String,
        @Header("tr_cont") trCont: String? = null,
        @Header("custtype") custType: String,
        @Header("seq_no") seqNo: String? = null,
        @Header("mac_address") macAddress: String? = null,
        @Header("phone_number") phoneNumber: String? = null,
        @Header("ip_addr") ipAddr: String? = null,
        @Header("hashkey") hashKey: String? = null,
        @Header("gt_uid") gtUid: String? = null,

        @Query("FID_COND_MRKT_DIV_CODE") condMrktDivCode: String,
        @Query("FID_INPUT_ISCD") inputIsCd: String,
        @Query("FID_INPUT_HOUR_1") inputHour1: String,
        @Query("FID_INPUT_DATE_1") inputDate1: String,
        @Query("FID_PW_DATA_INCU_YN") pwDataIncuYn: String,
        @Query("FID_FAKE_TICK_INCU_YN") fakeTickIncuYn: String? = null,
    ): StockCandleResponse

    @GET("/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice")
    suspend fun getMinutePrice(
        @Header("content-type") contentType: String,
        @Header("authorization") authorization: String,
        @Header("appkey") appkey: String,
        @Header("appsecret") appsecret: String,
        @Header("personalseckey") personalseckey: String? = null,
        @Header("tr_id") trId: String,
        @Header("tr_cont") trCont: String? = null,
        @Header("custtype") custtype: String,
        @Header("seq_no") seqNo: String? = null,
        @Header("mac_address") macAddress: String? = null,
        @Header("phone_number") phoneNumber: String? = null,
        @Header("ip_addr") ipAddr: String? = null,
        @Header("hashkey") hashkey: String? = null,
        @Header("gt_uid") gtUid: String? = null,

        @Query("FID_COND_MRKT_DIV_CODE") condMrktDivCode: String,
        @Query("FID_INPUT_ISCD") inputIsCd: String,
        @Query("FID_INPUT_HOUR_1") inputHour1: String,
        @Query("FID_PW_DATA_INCU_YN") pwDataIncuYn: String,
        @Query("FID_ETC_CLS_CODE") etcClsCode: String
    ): StockMinutePriceResponse

    @GET("/uapi/domestic-stock/v1/quotations/inquire-price")
    suspend fun getStockCurrentPrice(
        @Header("content-type") contentType: String = "application/json; charset=utf-8",
        @Header("authorization") authorization: String,
        @Header("appkey") appKey: String,
        @Header("appsecret") appSecret: String,
        @Header("personalseckey") personalSecKey: String? = null,
        @Header("tr_id") trId: String = "FHKST01010100",
        @Header("tr_cont") trCont: String? = null,
        @Header("custtype") custType: String,
        @Header("seq_no") seqNo: String? = null,
        @Header("mac_address") macAddress: String? = null,
        @Header("phone_number") phoneNumber: String? = null,
        @Header("ip_addr") ipAddr: String? = null,
        @Header("hashkey") hashKey: String? = null,
        @Header("gt_uid") gtUid: String? = null,
        @Query("FID_COND_MRKT_DIV_CODE") marketDivCode: String,
        @Query("FID_INPUT_ISCD") stockCode: String,
    ): StockPriceResponse
}