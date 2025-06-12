package com.jusicool.model.mapper.auth

import com.jusicool.entity.auth.SignInModel
import com.jusicool.model.auth.SignInRequest

fun SignInModel.toDto(): SignInRequest =
    SignInRequest(
        email = this.email,
        password = this.password
    )