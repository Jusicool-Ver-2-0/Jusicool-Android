package com.jusicool.usecase.auth

import com.jusicool.repository.auth.AuthRepository
import com.jusicool.model.auth.SignInRequest
import javax.inject.Inject

class SignInRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(body: SignInRequest) = runCatching {
        authRepository.signIn(body = body)
    }
}