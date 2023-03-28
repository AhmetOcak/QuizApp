package com.quizapp.data.datasource.remote.user.entity

import com.google.gson.annotations.SerializedName

data class LeaderboardDto(
    @SerializedName("userId")
    val id: String,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("userPhotoUrl")
    val userImage: String,

    @SerializedName("score")
    val score: Int
)
