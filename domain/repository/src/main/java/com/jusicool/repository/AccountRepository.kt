package com.jusicool.repository

import com.jusicool.entity.account.AccountModel
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccount(): Flow<AccountModel>
}