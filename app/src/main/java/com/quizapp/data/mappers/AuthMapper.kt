package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.auth.entity.AuthResponseDto
import com.quizapp.data.datasource.remote.auth.entity.LoginDto
import com.quizapp.data.datasource.remote.auth.entity.UserDto
import com.quizapp.domain.model.auth.AuthResponse
import com.quizapp.domain.model.auth.Login
import com.quizapp.domain.model.auth.User

fun Login.toLoginDto(): LoginDto {
    return LoginDto(
        email = email,
        password = password
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        userName = userName,
        email = email,
        password = password
    )
}

fun AuthResponseDto.toAuthResponse(): AuthResponse {
    return  AuthResponse(
        message = message
    )
}