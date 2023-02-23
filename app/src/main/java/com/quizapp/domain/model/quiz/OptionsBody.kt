package com.quizapp.domain.model.quiz

data class OptionsBody(
    val questionId: String,
    val options: ArrayList<OpBody>
)

data class OpBody(
    val description: String,
    val isAnswer: Boolean
)
