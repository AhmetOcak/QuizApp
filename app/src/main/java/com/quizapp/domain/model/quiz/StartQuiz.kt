package com.quizapp.domain.model.quiz

data class StartQuiz(
    val startedQuiz: StartedQuiz
)

data class StartedQuiz(
    val quizId: String,
    val title: String,
    val description: String,
    val categoryName: String,
    val questions: ArrayList<StartedQuizQuestions>
)

data class StartedQuizQuestions(
    val questionId: String,
    val title: String,
    val description: String,
    val options: ArrayList<StartedQuizOptions>
)

data class StartedQuizOptions(
    val optionId: String,
    val description: String
)
