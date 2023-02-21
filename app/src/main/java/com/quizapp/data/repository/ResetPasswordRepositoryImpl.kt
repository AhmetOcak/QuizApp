package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.reset_password.ResetPasswordRemoteDataSource
import com.quizapp.data.mappers.toResetPasswordDto
import com.quizapp.data.mappers.toSendPasswordResetMailDto
import com.quizapp.domain.model.reset_password.ResetPassword
import com.quizapp.domain.model.reset_password.SendPasswordResetMail
import com.quizapp.domain.repository.ResetPasswordRepository
import com.quizapp.domain.model.reset_password.ResetPasswordBody
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(private val resetPasswordRemoteDataSource: ResetPasswordRemoteDataSource) :
    ResetPasswordRepository {

    override suspend fun resetPassword(
        resetPassword: ResetPassword,
        resetPasswordBody: ResetPasswordBody
    ) =
        resetPasswordRemoteDataSource.resetPassword(
            resetPasswordDto = resetPassword.toResetPasswordDto(),
            resetPasswordBody = resetPasswordBody.toResetPasswordDto()
        )

    override suspend fun sendPasswordResetMail(sendPasswordResetMail: SendPasswordResetMail) =
        resetPasswordRemoteDataSource.sendPasswordResetMail(sendPasswordResetMailDto = sendPasswordResetMail.toSendPasswordResetMailDto())
}