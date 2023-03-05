package com.quizapp.domain.repository

import com.quizapp.domain.model.user.UpdateProfileBody
import com.quizapp.domain.model.user.UserProfile

interface UserRepository {

    suspend fun getUserProfile(token: String): UserProfile

    suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody)
}