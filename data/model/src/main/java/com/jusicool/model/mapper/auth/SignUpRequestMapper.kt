package com.jusicool.model.mapper.auth

import com.jusicool.entity.auth.SignUpModel
import com.jusicool.model.auth.SignUpRequest

fun SignUpModel.toDto(): SignUpRequest =
    SignUpRequest(
        username = this.username,
        email = this.email,
        password = this.password,
        school = this.school
    )