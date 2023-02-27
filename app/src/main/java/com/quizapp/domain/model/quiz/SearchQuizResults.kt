package com.quizapp.domain.model.quiz

data class SearchQuizResults(
    val results: Results
)

data class Results(
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val records: ArrayList<Records>
)

data class Records(
    val quizId: String,
    val title: String,
    val description: String
)