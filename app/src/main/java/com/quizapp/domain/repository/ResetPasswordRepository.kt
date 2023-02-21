package com.quizapp.domain.repository

import com.quizapp.domain.model.reset_password.ResetPassword
import com.quizapp.domain.model.reset_password.SendPasswordResetMail
import com.quizapp.domain.model.reset_password.ResetPasswordBody

interface ResetPasswordRepository {

    suspend fun resetPassword(resetPassword: ResetPassword, resetPasswordBody: ResetPasswordBody)

    suspend fun sendPasswordResetMail(sendPasswordResetMail: SendPasswordResetMail)
}