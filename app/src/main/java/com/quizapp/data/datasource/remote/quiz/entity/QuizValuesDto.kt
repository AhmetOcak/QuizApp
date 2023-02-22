package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class QuizValuesDto(
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("userId") var userId: String,
    @SerializedName("score") var score: Int,
    @SerializedName("categoryId") var categoryId: String,
    @SerializedName("category") var category: String,
    @SerializedName("isVisible") var isVisible: Boolean,
    @SerializedName("questions") var questions: ArrayList<QuestionsDto>,
    @SerializedName("id") var id: String,
    @SerializedName("createdDate") var createdDate: String
)

data class QuestionsDto(
    @SerializedName("quizId") val quizId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("options") val options: ArrayList<OptionsDto>,
    @SerializedName("id") val id: String,
    @SerializedName("createdDate") val createdDate: String
)