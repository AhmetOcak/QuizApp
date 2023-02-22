package com.quizapp.data.datasource.remote.question.entity

data class CreateQuestionDto(
    val quizId: String,
    val title: String,
    val description: String
)