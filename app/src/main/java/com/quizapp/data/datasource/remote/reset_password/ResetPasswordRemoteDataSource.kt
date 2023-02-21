package com.quizapp.data.datasource.remote.reset_password

import com.quizapp.data.datasource.remote.reset_password.entity.ResetPasswordBodyDto
import com.quizapp.data.datasource.remote.reset_password.entity.ResetPasswordDto
import com.quizapp.data.datasource.remote.reset_password.entity.SendPasswordResetMailDto

interface ResetPasswordRemoteDataSource {

    suspend fun resetPassword(resetPasswordDto: ResetPasswordDto, resetPasswordBody: ResetPasswordBodyDto)

    suspend fun sendPasswordResetMail(sendPasswordResetMailDto: SendPasswordResetMailDto)
}