package com.quizapp.data.datasource.remote.auth.entity

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("message")
    val message: String? = null
)
