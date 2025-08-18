package com.jusicool.usecase.account

import com.jusicool.entity.account.AccountModel
import com.jusicool.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountResponseUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(): Flow<AccountModel> =
        accountRepository.getAccount()
}