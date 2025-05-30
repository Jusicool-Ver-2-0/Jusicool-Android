package com.jusicool.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInRequest (
    val email: String,
    val password: String
)