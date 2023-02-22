package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class QuizzesDto(
    @SerializedName("quizzes") val quizzes: QuizzesInfoDto
)

data class QuizzesInfoDto(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("records") val records: ArrayList<QuizDto>
)

data class QuizDto(
    @SerializedName("quizId") val quizId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)

data class QuizzesQueryDto(
    val page: Int,
    val pageSize: Int
)
