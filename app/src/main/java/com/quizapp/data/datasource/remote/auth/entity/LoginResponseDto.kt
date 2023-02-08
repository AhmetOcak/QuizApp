package com.quizapp.data.datasource.remote.auth.entity

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("token")
    val token: LoginResponseBodyDto
)

data class LoginResponseBodyDto(
    @SerializedName("accessToken")
    val accessToken: String
)
