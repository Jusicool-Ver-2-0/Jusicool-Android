package com.jusicool.network.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.jusicool.model.auth.SignInRequest
import com.jusicool.model.auth.SignUpRequest

interface AuthApi {
    @POST("/user/signin")
    suspend fun signIn(
        @Body body: SignInRequest
    )

    @POST("/user/signup")
    suspend fun signUp(
        @Body body: SignUpRequest
    )
}