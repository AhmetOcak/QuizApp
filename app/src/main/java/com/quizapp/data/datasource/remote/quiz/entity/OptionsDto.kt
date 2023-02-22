package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class OptionsDto(
    @SerializedName("questionId") val questionId: String,
    @SerializedName("description") val description: String,
    @SerializedName("isAnswer") val isAnswer: Boolean,
    @SerializedName("id") val id: String,
    @SerializedName("createdDate") val createdDate: String
)
