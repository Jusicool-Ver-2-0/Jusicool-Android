package com.jusicool.network.datasource.auth

import com.jusicool.model.auth.SignInRequest
import com.jusicool.model.auth.SignUpRequest
import com.jusicool.model.auth.VerificationEmailRequest
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun authSignIn(body: SignInRequest): Flow<Unit>

    fun authSignUp(body: SignUpRequest): Flow<Unit>

    fun postVerificationEmail(body: VerificationEmailRequest): Flow<Unit>
}