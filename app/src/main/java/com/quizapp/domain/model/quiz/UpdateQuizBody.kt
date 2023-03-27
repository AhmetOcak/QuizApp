package com.quizapp.domain.model.quiz

data class UpdateQuizBody(
    val id: String,
    val title: String,
    val description: String
)
