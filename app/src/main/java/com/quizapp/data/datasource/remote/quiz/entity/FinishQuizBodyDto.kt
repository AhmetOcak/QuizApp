package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class FinishQuizBodyDto(
    @SerializedName("quiz") val quiz: FinishedQuizBodyDto
)

data class FinishedQuizBodyDto(
    @SerializedName("quizId") val quizId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("questions") val questions: ArrayList<AnswersBodyDto>
)

data class AnswersBodyDto(
    @SerializedName("questionId") val questionId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("selectedOptionId") val selectedOptionId: String,
    @SerializedName("selectedOptionDescription") val selectedOptionDescription: String
)