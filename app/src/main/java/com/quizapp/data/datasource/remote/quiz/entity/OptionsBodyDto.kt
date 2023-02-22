package com.quizapp.data.datasource.remote.quiz.entity

data class OptionsBodyDto(
    val options: ArrayList<OpBodyDto>
)

data class OpBodyDto(
    val questionId: String,
    val description: String,
    val isAnswer: Boolean
)