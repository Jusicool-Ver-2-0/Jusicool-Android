package com.jusicool.network.datasource.account

import com.jusicool.model.account.AccountResponse
import kotlinx.coroutines.flow.Flow

interface AccountDataSource {
    fun getAccount(): Flow<AccountResponse>
}