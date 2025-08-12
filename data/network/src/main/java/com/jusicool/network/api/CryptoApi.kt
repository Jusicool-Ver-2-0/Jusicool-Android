package com.jusicool.network.api

import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import com.jusicool.model.crypto.CurrentMinuteCandleResponse
import com.jusicool.model.crypto.DayCandleResponse
import com.jusicool.model.crypto.MinuteCandleResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApi {
    @GET("/v1/ticker")
    suspend fun getCurrentCryptoPrice(
        @Query("markets") markets: String
    ): List<CurrentCryptoPriceResponse>

    @GET("/v1/candles/minutes/{unit}")
    suspend fun getMinuteCandle(
        @Path("unit") unit: Int = 1, //나중에 시간 조절
        @Query("market") market: String,
        @Query("to") to: String,
        @Query("count") count: Int
    ): List<MinuteCandleResponse>

    @GET("/v1/candles/minutes/{unit}")
    suspend fun getCurrentMinuteCandle(
        @Path("unit") unit: Int = 1,
        @Query("market") market: String,
        @Query("to") to: String,
        @Query("count") count: Int = 1
    ): List<CurrentMinuteCandleResponse>

    @GET("/v1/candles/days/")
    suspend fun getDayCandle(
        @Query("market") market: String,
        @Query("to") to: String,
        @Query("count") count: Int
    ): List<DayCandleResponse>
}