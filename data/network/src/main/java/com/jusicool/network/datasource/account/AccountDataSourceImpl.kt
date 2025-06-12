package com.jusicool.network.datasource.account

import com.jusicool.model.account.AccountResponse
import com.jusicool.network.api.AccountApi
import com.jusicool.utils.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountDataSourceImpl @Inject constructor(
    private val accountApi: AccountApi
): AccountDataSource {
    override fun getAccount(): Flow<AccountResponse> =
        performApiRequest { accountApi.getAccount() }
}