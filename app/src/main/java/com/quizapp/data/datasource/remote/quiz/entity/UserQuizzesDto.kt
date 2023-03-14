package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class UserQuizzesDto(

    @SerializedName("quizzes") val quizzes: ArrayList<UserQuizzesDetailDto>
)

data class UserQuizzesDetailDto(

    @SerializedName("quizId") val quizId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userPhotoUrl") val userPhotoUrl: String,
    @SerializedName("quizCreatedDate") val quizCreatedDate: String
)
