package com.quizapp.data.datasource.remote.quiz.entity

import com.google.gson.annotations.SerializedName

data class StartQuizDto(
    @SerializedName("quiz") val startedQuiz: StartedQuizDto
)

data class StartedQuizDto(
    @SerializedName("quizId") val quizId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("questions") val questions: ArrayList<StartedQuizQuestionsDto>

)

data class StartedQuizQuestionsDto(
    @SerializedName("questionId") val questionId: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("options") val options: ArrayList<StartedQuizOptionsDto>
)

data class StartedQuizOptionsDto(
    @SerializedName("optionId") val optionId: String,
    @SerializedName("description") val description: String
)
