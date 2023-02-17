package com.quizapp.domain.repository

import com.quizapp.domain.model.reset_password.ResetPassword
import com.quizapp.domain.model.reset_password.SendPasswordResetMail

interface ResetPasswordRepository {

    suspend fun resetPassword(resetPassword: ResetPassword)

    suspend fun sendPasswordResetMail(sendPasswordResetMail: SendPasswordResetMail)
}