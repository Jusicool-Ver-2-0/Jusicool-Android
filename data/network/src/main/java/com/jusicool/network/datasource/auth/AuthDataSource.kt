package com.jusicool.network.datasource.auth

import com.jusicool.model.auth.SignInRequest
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun authSignIn(body: SignInRequest): Flow<Unit>
}