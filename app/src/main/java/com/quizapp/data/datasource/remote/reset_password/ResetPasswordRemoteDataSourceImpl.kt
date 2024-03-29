package com.quizapp.data.datasource.remote.reset_password

import com.quizapp.data.datasource.remote.reset_password.api.ResetPasswordApi
import com.quizapp.data.datasource.remote.reset_password.entity.ResetPasswordBodyDto
import com.quizapp.data.datasource.remote.reset_password.entity.ResetPasswordDto
import com.quizapp.data.datasource.remote.reset_password.entity.SendPasswordResetMailDto
import javax.inject.Inject

class ResetPasswordRemoteDataSourceImpl @Inject constructor(private val api: ResetPasswordApi) :
    ResetPasswordRemoteDataSource {

    override suspend fun resetPassword(resetPasswordDto: ResetPasswordDto, resetPasswordBody: ResetPasswordBodyDto) =
        api.resetPassword(newPassword = resetPasswordBody, email = resetPasswordDto.email, token = resetPasswordDto.token)

    override suspend fun sendPasswordResetMail(sendPasswordResetMailDto: SendPasswordResetMailDto) =
        api.sendPasswordResetMail(email = sendPasswordResetMailDto.email)
}