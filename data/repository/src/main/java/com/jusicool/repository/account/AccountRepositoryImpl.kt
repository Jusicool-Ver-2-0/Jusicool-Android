package com.jusicool.repository.account

import kotlinx.coroutines.flow.map
import com.jusicool.entity.account.AccountModel
import com.jusicool.model.mapper.account.toModel
import com.jusicool.network.datasource.account.AccountDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource
): AccountRepository {
    override fun getAccount(): Flow<AccountModel> {
        return accountDataSource.getAccount().map { it.toModel() }
    }
}