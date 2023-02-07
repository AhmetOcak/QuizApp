package com.quizapp.data.datasource.remote.auth

import com.quizapp.data.datasource.remote.auth.entity.AuthResponseDto
import com.quizapp.data.datasource.remote.auth.entity.UserDto

interface AuthRemoteDataSource {

    suspend fun createUser(userDto: UserDto) : AuthResponseDto
}