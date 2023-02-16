package com.quizapp.data.datasource.remote.confirm_account

import com.quizapp.data.datasource.remote.confirm_account.api.ConfirmAccountApi
import com.quizapp.data.datasource.remote.confirm_account.entity.ConfirmAccountDto
import javax.inject.Inject

class ConfirmAccountRemoteDataSourceImpl @Inject constructor(
    private val api: ConfirmAccountApi
) : ConfirmAccountRemoteDataSource {

    override suspend fun confirmAccount(confirmAccountDto: ConfirmAccountDto) =
        api.confirmMail(email = confirmAccountDto.email, token = confirmAccountDto.token)
}