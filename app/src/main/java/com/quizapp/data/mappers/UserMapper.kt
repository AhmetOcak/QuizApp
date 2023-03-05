package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.user.entity.UpdatePasswordBodyDto
import com.quizapp.data.datasource.remote.user.entity.UpdateProfileBodyDto
import com.quizapp.data.datasource.remote.user.entity.UserProfileDto
import com.quizapp.domain.model.user.UpdatePasswordBody
import com.quizapp.domain.model.user.UpdateProfileBody
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

fun UpdatePasswordBody.toUpdatePasswordBodyDto(): UpdatePasswordBodyDto {
    return UpdatePasswordBodyDto(
        oldPassword = oldPassword,
        newPassword = newPassword
    )
}

fun UpdateProfileBody.toUpdateProfileBodyDto(): UpdateProfileBodyDto {
    return UpdateProfileBodyDto(
        firstName = firstName,
        lastName = lastName,
        biography = biography
    )
}