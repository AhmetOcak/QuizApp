package com.quizapp.domain.repository

import com.quizapp.domain.model.auth.AuthResponse
import com.quizapp.domain.model.auth.Login
import com.quizapp.domain.model.auth.LoginResponse
import com.quizapp.domain.model.auth.User

interface AuthRepository {

    suspend fun createUser(user: User) : AuthResponse

    suspend fun signIn(login: Login) : LoginResponse
}