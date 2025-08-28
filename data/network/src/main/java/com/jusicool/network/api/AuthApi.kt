package com.jusicool.network.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.jusicool.model.auth.SignInRequest
import com.jusicool.model.auth.SignUpRequest
import com.jusicool.model.auth.VerificationCodeRequest
import com.jusicool.model.auth.VerificationEmailRequest

interface AuthApi {
    @POST("/user/signin")
    suspend fun signIn(
        @Body body: SignInRequest
    )

    @POST("/user/signup")
    suspend fun signUp(
        @Body body: SignUpRequest
    )

    @POST("/user/email/send")
    suspend fun verificationEmailRequest(
        @Body body: VerificationEmailRequest
    )

    @POST("/user/email/verify")
    suspend fun verificationCodeRequest(
        @Body body: VerificationCodeRequest
    )
}