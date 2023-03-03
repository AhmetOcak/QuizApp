package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class FinishQuizResponseDto(
    @SerializedName("quizResult") val quizResult: QuizResultResponseDto
)

data class QuizResultResponseDto(
    @SerializedName("quizId") val quizId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("correctAnswerCount") val correctAnswerCount: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("questions") val questions: ArrayList<QuestionsResponseDto>
)

data class QuestionsResponseDto(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("selectedOptionDescription") val selectedOptionDescription: String,
    @SerializedName("isCorrect") val isCorrect: Boolean
)
