package com.quizapp.domain.model.option

data class Options(
    val options: ArrayList<Option>
)

data class Option(
    val optionId: String,
    val description: String,
    val isAnswer: Boolean
)
