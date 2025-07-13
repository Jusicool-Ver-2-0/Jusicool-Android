package com.jusicool.usecase.account

import com.jusicool.repository.AccountRepository
import javax.inject.Inject

class GetAccountResponseUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke() =
        accountRepository.getAccount()
}