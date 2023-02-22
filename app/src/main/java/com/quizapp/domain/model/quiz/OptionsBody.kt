package com.quizapp.domain.model.quiz

data class OptionsBody(
    val options: ArrayList<OpBody>
)

data class OpBody(
    val questionId: String,
    val description: String,
    val isAnswer: Boolean
)
