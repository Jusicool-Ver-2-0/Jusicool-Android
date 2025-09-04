package com.jusicool.network.datasource.auth

import com.jusicool.model.auth.SignInRequest
import com.jusicool.model.auth.SignUpRequest
import com.jusicool.model.auth.VerificationCodeRequest
import com.jusicool.model.auth.VerificationEmailRequest
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun authSignIn(body: SignInRequest): Flow<Unit>

    fun authSignUp(body: SignUpRequest): Flow<Unit>

    fun verificationEmail(body: VerificationEmailRequest): Flow<Unit>

    fun verificationCode(body: VerificationCodeRequest): Flow<Unit>
}