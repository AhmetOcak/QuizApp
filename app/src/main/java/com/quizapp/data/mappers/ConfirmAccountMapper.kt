package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.confirm_account.entity.ConfirmAccountDto
import com.quizapp.domain.model.confirm_account.ConfirmAccount

fun ConfirmAccount.toConfirmAccountDto(): ConfirmAccountDto {
    return ConfirmAccountDto(
        email = email,
        token = token
    )
}