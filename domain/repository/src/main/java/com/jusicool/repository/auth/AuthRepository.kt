package com.jusicool.repository.auth

import com.jusicool.model.auth.SignInRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(body: SignInRequest): Flow<Unit>
}