package com.jusicool.usecase.auth

import com.jusicool.entity.auth.SignUpModel
import com.jusicool.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(body: SignUpModel): Flow<Unit> =
        authRepository.signUp(body = body)
}