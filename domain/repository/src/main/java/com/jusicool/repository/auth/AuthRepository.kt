package com.jusicool.repository.auth

import com.jusicool.entity.auth.SignInModel
import com.jusicool.model.auth.SignInRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(body: SignInModel): Flow<Unit>
}