package com.quizapp.domain.model.auth

data class LoginResponse(
    val token: LoginResponseBody
)

data class LoginResponseBody(
    val accessToken: String
)
