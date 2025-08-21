package com.jusicool.repository

import com.jusicool.entity.auth.SignInModel
import com.jusicool.entity.auth.SignUpModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(body: SignInModel): Flow<Unit>

    fun signUp(body: SignUpModel): Flow<Unit>
}