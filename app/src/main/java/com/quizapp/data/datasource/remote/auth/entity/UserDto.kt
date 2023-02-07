package com.quizapp.data.datasource.remote.auth.entity

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("userName")
    val userName: String,

    @SerializedName("eMail")
    val email: String,

    @SerializedName("password")
    val password: String
)
