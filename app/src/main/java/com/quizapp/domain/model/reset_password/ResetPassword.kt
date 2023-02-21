package com.quizapp.domain.model.reset_password

data class ResetPassword(
    val email: String,
    val token: String
)
