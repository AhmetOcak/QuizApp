package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.confirm_account.ConfirmAccountRemoteDataSource
import com.quizapp.data.mappers.toConfirmAccountDto
import com.quizapp.domain.model.confirm_account.ConfirmAccount
import com.quizapp.domain.repository.ConfirmAccountRepository
import javax.inject.Inject

class ConfirmAccountRepositoryImpl @Inject constructor(
    private val confirmAccountRemoteDataSource: ConfirmAccountRemoteDataSource
) : ConfirmAccountRepository {

    override suspend fun confirmAccount(confirmAccount: ConfirmAccount) =
        confirmAccountRemoteDataSource.confirmAccount(confirmAccountDto = confirmAccount.toConfirmAccountDto())
}