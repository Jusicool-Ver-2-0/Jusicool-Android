package com.jusicool.model.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerificationEmailRequest(
    val email: String
)