package com.quizapp.domain.model.quiz

data class Options(
    val questionId: String,
    val description: String,
    val isAnswer: Boolean,
    val id: String,
    val createdDate: String
)
