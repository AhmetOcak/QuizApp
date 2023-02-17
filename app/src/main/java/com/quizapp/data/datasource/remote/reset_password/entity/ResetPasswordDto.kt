package com.quizapp.data.datasource.remote.reset_password.entity

data class ResetPasswordDto(
    val email: String,
    val token: String,
    val newPassword: String
)
