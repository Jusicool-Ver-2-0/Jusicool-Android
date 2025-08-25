package com.jusicool.usecase.auth

import com.jusicool.entity.auth.VerificationEmailModel
import com.jusicool.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerificationEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(body: VerificationEmailModel): Flow<Unit> =
        authRepository.postVerificationEmail(body = body)
}