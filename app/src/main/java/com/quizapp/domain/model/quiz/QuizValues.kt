package com.quizapp.domain.model.quiz

data class QuizValues(
    val title: String,
    val description: String,
    val userId: String,
    val score: Int,
    val categoryId: String,
    val category: String?,
    val isVisible: Boolean,
    val questions: ArrayList<Question>,
    val id: String,
    val createdDate: String
)

data class Question(
    val quizId: String,
    val title: String,
    val description: String,
    val options: ArrayList<Options>,
    val id: String,
    val createdDate: String
)
