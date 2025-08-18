package com.jusicool.usecase.auth

import com.jusicool.entity.auth.SignInModel
import com.jusicool.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(body: SignInModel): Flow<Unit> =
        authRepository.signIn(body = body)
}