package com.jusicool.repository

import com.jusicool.entity.auth.SignInModel
import com.jusicool.entity.auth.SignUpModel
import com.jusicool.entity.auth.VerificationCodeModel
import com.jusicool.entity.auth.VerificationEmailModel
import com.jusicool.model.mapper.auth.toDto
import com.jusicool.network.datasource.auth.AuthDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
): AuthRepository {
    override fun signIn(body: SignInModel): Flow<Unit> {
        return authDataSource.authSignIn(
            body = body.toDto()
        )
    }

    override fun signUp(body: SignUpModel): Flow<Unit> {
        return authDataSource.authSignUp(
            body = body.toDto()
        )
    }

    override fun verificationEmail(body: VerificationEmailModel): Flow<Unit> {
        return authDataSource.verificationEmail(
            body = body.toDto()
        )
    }

    override fun verificationCode(body: VerificationCodeModel): Flow<Unit> {
        return authDataSource.verificationCode(
            body = body.toDto()
        )
    }
}