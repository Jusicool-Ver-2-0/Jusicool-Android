package com.jusicool.network.api

import com.jusicool.model.crypto.CurrentCryptoPriceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("/v1/ticker")
    suspend fun getCurrentCryptoPrice(
        @Query("markets") markets: String
    ): List<CurrentCryptoPriceResponse>
}