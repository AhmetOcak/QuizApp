package com.quizapp.domain.repository

import com.quizapp.data.datasource.remote.auth.entity.AuthResponseDto
import com.quizapp.domain.model.auth.AuthResponse
import com.quizapp.domain.model.auth.User

interface AuthRepository {

    suspend fun createUser(user: User) : AuthResponse
}