package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.auth.entity.*
import com.quizapp.domain.model.auth.*

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
    return AuthResponse(
        message = message
    )
}

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        token = LoginResponseBody(accessToken = token.accessToken)
    )
}