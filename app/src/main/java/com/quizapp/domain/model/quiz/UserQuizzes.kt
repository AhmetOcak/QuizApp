package com.quizapp.domain.model.quiz

data class UserQuizzes(
    val quizzes: ArrayList<UserQuizzesDetail>
)

data class UserQuizzesDetail(
    val quizId: String,
    val title: String,
    val description: String,
    val categoryName: String,
    val userName: String?,
    val userPhotoUrl: String?,
    val quizCreatedDate: String
)
