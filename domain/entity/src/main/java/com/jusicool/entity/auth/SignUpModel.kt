package com.jusicool.entity.auth

data class SignUpModel(
    val username: String,
    val email: String,
    val password: String,
    val school: String
)