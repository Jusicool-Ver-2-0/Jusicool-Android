package com.jusicool.network.api

import com.jusicool.model.account.AccountResponse
import retrofit2.http.GET

interface AccountApi {
    @GET("/account/my")
    suspend fun getAccount(): AccountResponse
}