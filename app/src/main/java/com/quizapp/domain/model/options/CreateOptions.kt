package com.quizapp.domain.model.options

data class CreateOptions(
    val questionId: String,
    val description: String,
    val isAnswer: Boolean
)
