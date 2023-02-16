package com.quizapp.data.datasource.remote.confirm_account

import com.quizapp.data.datasource.remote.confirm_account.entity.ConfirmAccountDto

interface ConfirmAccountRemoteDataSource {

    suspend fun confirmAccount(confirmAccountDto: ConfirmAccountDto)
}