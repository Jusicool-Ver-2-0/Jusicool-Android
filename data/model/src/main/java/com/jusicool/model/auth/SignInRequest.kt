package com.jusicool.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInRequest (
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)