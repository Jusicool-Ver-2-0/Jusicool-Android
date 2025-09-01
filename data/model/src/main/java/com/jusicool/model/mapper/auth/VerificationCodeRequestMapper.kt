package com.jusicool.model.mapper.auth

import com.jusicool.entity.auth.VerificationCodeModel
import com.jusicool.model.auth.VerificationCodeRequest

fun VerificationCodeModel.toDto(): VerificationCodeRequest =
    VerificationCodeRequest(
        email = this.email,
        code = this.code
    )