package com.quizapp.domain.model.question

data class CreateQuestion(
    val quizId: String,
    val title: String,
    val description: String
)
