package com.jusicool.entity.auth

data class VerificationCodeModel(
    val email: String,
    val code: Int
)