package com.jusicool.network.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.jusicool.model.auth.SignInRequest

interface AuthApi {
    @POST("/user/signin")
    suspend fun signIn(
        @Body body: SignInRequest
    )
}