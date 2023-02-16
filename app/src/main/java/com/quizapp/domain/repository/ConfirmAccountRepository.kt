package com.quizapp.domain.repository

import com.quizapp.domain.model.confirm_account.ConfirmAccount

interface ConfirmAccountRepository {

    suspend fun confirmAccount(confirmAccount: ConfirmAccount)
}