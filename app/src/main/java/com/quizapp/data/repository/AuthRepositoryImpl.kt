package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.quizapp.data.mappers.toAuthResponse
import com.quizapp.data.mappers.toLoginDto
import com.quizapp.data.mappers.toLoginResponse
import com.quizapp.data.mappers.toUserDto
import com.quizapp.domain.model.auth.Login
import com.quizapp.domain.model.auth.LoginResponse
import com.quizapp.domain.model.auth.User
import com.quizapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun createUser(user: User) =
        remoteDataSource.createUser(userDto = user.toUserDto()).toAuthResponse()

    override suspend fun signIn(login: Login): LoginResponse =
        remoteDataSource.signIn(loginDto = login.toLoginDto()).toLoginResponse()
}