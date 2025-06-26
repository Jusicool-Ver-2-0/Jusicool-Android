package com.jusicool.repository

import com.jusicool.entity.auth.SignInModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(body: SignInModel): Flow<Unit>
}