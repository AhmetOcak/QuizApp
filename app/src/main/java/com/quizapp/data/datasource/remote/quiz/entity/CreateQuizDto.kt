package com.quizapp.data.datasource.remote.quiz.entity

data class CreateQuizDto(
    val title: String,
    val description: String,
    val categoryId: String
)
