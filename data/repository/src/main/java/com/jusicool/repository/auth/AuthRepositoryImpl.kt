package com.jusicool.repository.auth

import com.jusicool.entity.auth.SignInModel
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
}