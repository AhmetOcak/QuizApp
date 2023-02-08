package com.quizapp.data.datasource.remote.auth

import com.quizapp.data.datasource.remote.auth.entity.*

interface AuthRemoteDataSource {

    suspend fun createUser(userDto: UserDto) : AuthResponseDto

    suspend fun signIn(loginDto: LoginDto) : LoginResponseDto
}