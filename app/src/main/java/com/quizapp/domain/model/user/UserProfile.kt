package com.quizapp.domain.model.user

data class UserProfile(
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val score: Int,
    val profilePictureUrl: String,
    val biography: String?
)
