package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.user.entity.UserProfileDto
import com.quizapp.domain.model.user.UserProfile

fun UserProfileDto.toUserProfile(): UserProfile {
    return UserProfile(
        userName = userName,
        firstName = firstName,
        lastName = lastName,
        profilePictureUrl = profilePictureUrl,
        score = score,
        biography = biography
    )
}