package com.quizapp.domain.model.quiz

data class FinishQuizBody(
    val quiz: FinishedBodyQuiz
)

data class FinishedBodyQuiz(
    val quizId: String,
    val title: String,
    val description: String,
    val questions: ArrayList<AnswersBody>
)

data class AnswersBody(
    val questionId: String,
    val title: String,
    val description: String,
    val selectedOptionId: String,
    val selectedOptionDescription: String
)