package com.quizapp.data.datasource.remote.user

import com.quizapp.data.datasource.remote.user.entity.UpdateProfileBodyDto
import com.quizapp.data.datasource.remote.user.entity.UserProfileDto

interface UserRemoteDataSource {

    suspend fun getUserProfile(token: String): UserProfileDto

    suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto)
}