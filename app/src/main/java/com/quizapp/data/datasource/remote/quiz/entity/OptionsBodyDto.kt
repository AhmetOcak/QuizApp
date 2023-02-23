package com.quizapp.data.datasource.remote.quiz.entity

data class OptionsBodyDto(
    val questionId: String,
    val options: ArrayList<OpBodyDto>
)

data class OpBodyDto(
    val description: String,
    val isAnswer: Boolean
)