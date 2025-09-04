package com.jusicool.model.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerificationCodeRequest(
    val email: String,
    val code: Int
)