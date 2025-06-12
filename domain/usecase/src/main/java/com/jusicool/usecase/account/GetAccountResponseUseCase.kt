package com.jusicool.usecase.account

import com.jusicool.repository.account.AccountRepository
import javax.inject.Inject

class GetAccountResponseUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke() = runCatching {
        accountRepository.getAccount()
    }
}