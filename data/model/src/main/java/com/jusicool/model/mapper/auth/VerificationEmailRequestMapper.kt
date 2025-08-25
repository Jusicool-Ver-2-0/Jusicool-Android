package com.jusicool.model.mapper.auth

import com.jusicool.entity.auth.VerificationEmailModel
import com.jusicool.model.auth.VerificationEmailRequest

fun VerificationEmailModel.toDto(): VerificationEmailRequest =
    VerificationEmailRequest(
        email = this.email
    )