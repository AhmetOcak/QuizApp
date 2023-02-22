package com.quizapp.data.datasource.remote.options.entity

data class CreateOptionsDto(
    val questionId: String,
    val description: String,
    val isAnswer: Boolean
)
