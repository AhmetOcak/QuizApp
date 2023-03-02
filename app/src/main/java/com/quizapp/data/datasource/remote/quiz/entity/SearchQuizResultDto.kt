package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class SearchQuizResultDto(
    @SerializedName("quizzes") val results: ResultsDto
)

data class ResultsDto(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("records") val records: ArrayList<RecordsDto>
)

data class RecordsDto(
    @SerializedName("quizId") val quizId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("userName") val authorUserName: String,
    @SerializedName("userPhotoUrl") val authorUserImage: String,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("quizCreatedDate") val quizCreatedDate: String
)
