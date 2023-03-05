package com.quizapp.domain.model.user

data class UpdatePasswordBody(
    val oldPassword: String,
    val newPassword: String
)
