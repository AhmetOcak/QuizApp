package com.quizapp.domain.model.question

data class QuestionList(
    val questionList: ArrayList<Question>
)

data class Question(
    val questionId: String,
    val title: String,
    val description: String,
    val options: ArrayList<Option>
)

data class Option(
    val optionId: String,
    val description: String
)
