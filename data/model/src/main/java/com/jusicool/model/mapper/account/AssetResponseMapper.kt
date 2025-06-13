package com.jusicool.model.mapper.account

import com.jusicool.entity.account.AccountModel
import com.jusicool.model.account.AccountResponse

fun AccountResponse.toModel(): AccountModel =
    AccountModel(
        id = this.id,
        krwBalance = this.krwBalance
    )