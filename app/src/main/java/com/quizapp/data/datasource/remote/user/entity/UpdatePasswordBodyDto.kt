package com.quizapp.data.datasource.remote.user.entity

data class UpdatePasswordBodyDto(
    val oldPassword: String,
    val newPassword: String
)
