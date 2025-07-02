package com.jusicool.usecase.auth

import com.jusicool.entity.auth.SignInModel
import com.jusicool.repository.AuthRepository
import javax.inject.Inject

class SignInRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(body: SignInModel) = runCatching {
        authRepository.signIn(body = body)
    }
}