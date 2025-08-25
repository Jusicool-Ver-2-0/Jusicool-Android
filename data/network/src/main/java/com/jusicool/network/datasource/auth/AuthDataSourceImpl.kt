package com.jusicool.network.datasource.auth

import com.jusicool.model.auth.SignInRequest
import com.jusicool.model.auth.SignUpRequest
import com.jusicool.model.auth.VerificationEmailRequest
import com.jusicool.network.api.AuthApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val service: AuthApi
) : AuthDataSource {
    override fun authSignIn(body: SignInRequest): Flow<Unit> =
        performApiRequest { service.signIn(body = body) }

    override fun authSignUp(body: SignUpRequest): Flow<Unit> =
        performApiRequest { service.signUp(body = body) }

    override fun verificationEmail(body: VerificationEmailRequest): Flow<Unit> =
        performApiRequest { service.verificationEmailRequest(body = body) }
}