package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.reset_password.entity.ResetPasswordBodyDto
import com.quizapp.data.datasource.remote.reset_password.entity.ResetPasswordDto
import com.quizapp.data.datasource.remote.reset_password.entity.SendPasswordResetMailDto
import com.quizapp.domain.model.reset_password.ResetPassword
import com.quizapp.domain.model.reset_password.SendPasswordResetMail
import com.quizapp.domain.model.reset_password.ResetPasswordBody

fun ResetPassword.toResetPasswordDto(): ResetPasswordDto {
    return ResetPasswordDto(
        email = email,
        token = token
    )
}

fun ResetPasswordBody.toResetPasswordDto(): ResetPasswordBodyDto {
    return ResetPasswordBodyDto(newPassword = newPassword)
}

fun SendPasswordResetMail.toSendPasswordResetMailDto(): SendPasswordResetMailDto {
    return SendPasswordResetMailDto(
        email = email
    )
}