package com.quizapp.data.datasource.remote.user

import com.quizapp.data.datasource.remote.user.entity.UpdatePasswordBodyDto
import com.quizapp.data.datasource.remote.user.entity.UpdateProfileBodyDto
import com.quizapp.data.datasource.remote.user.entity.UserProfileDto

interface UserRemoteDataSource {

    suspend fun getUserProfile(token: String): UserProfileDto

    suspend fun updatePassword(token: String, updatePasswordBodyDto: UpdatePasswordBodyDto)

    suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto)
}