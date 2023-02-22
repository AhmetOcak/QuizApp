package com.quizapp.domain.model.quiz

data class Quizzes(
    val quizzes: QuizzesInfo
)

data class QuizzesInfo(
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val records: ArrayList<Quiz>
)

data class Quiz(
    val quizId: String,
    val title: String,
    val description: String
)

data class QuizzesQuery(
    val page: Int,
    val pageSize: Int
)
