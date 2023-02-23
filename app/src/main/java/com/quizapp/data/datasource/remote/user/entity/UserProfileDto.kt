package com.quizapp.data.datasource.remote.user.entity

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("userName") val userName: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("score") val score: Int,
    @SerializedName("profilePictureUrl") val profilePictureUrl: String,
    @SerializedName("biography") val biography: String
)
