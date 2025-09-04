package com.jusicool.model.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String,
    val school: String
)