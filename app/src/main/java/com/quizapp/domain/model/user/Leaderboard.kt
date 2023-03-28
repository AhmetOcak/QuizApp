package com.quizapp.domain.model.user

data class Leaderboard(
    val id: String,
    val userName: String,
    val userImage: String,
    val score: Int
)