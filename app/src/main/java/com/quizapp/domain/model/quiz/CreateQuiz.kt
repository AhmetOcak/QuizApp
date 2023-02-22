package com.quizapp.domain.model.quiz

data class CreateQuiz(
    val title: String,
    val description: String,
    val categoryId: String
)
