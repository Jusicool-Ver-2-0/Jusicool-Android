package com.jusicool.network.api

import com.jusicool.model.auth.SignInRequest
import com.jusicool.model.community.WritePostRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CommunityApi {
    @POST("/community/{market}")
    suspend fun postWrite(
        @Path("market") market: String,
        @Body body: WritePostRequest
    )
}