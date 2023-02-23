package com.quizapp.data.datasource.remote.user

import com.quizapp.data.datasource.remote.user.entity.UserProfileDto

interface UserRemoteDataSource {

    suspend fun getUserProfile(token: String): UserProfileDto
}