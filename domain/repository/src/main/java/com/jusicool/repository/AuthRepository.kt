package com.jusicool.repository

import com.jusicool.entity.auth.SignInModel
import com.jusicool.entity.auth.SignUpModel
import com.jusicool.entity.auth.VerificationCodeModel
import com.jusicool.entity.auth.VerificationEmailModel
import com.jusicool.model.auth.VerificationEmailRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(body: SignInModel): Flow<Unit>

    fun signUp(body: SignUpModel): Flow<Unit>

    fun verificationEmail(body: VerificationEmailModel): Flow<Unit>

    fun verificationCode(body: VerificationCodeModel): Flow<Unit>
}