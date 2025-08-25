package com.jusicool.usecase.auth

import com.jusicool.entity.auth.VerificationCodeModel
import com.jusicool.model.mapper.auth.toDto
import com.jusicool.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerificationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(body: VerificationCodeModel): Flow<Unit> =
        authRepository.verificationCode(body = body)
}