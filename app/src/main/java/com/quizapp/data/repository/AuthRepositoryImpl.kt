package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.quizapp.data.mappers.toAuthResponse
import com.quizapp.data.mappers.toUserDto
import com.quizapp.domain.model.auth.User
import com.quizapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun createUser(user: User) =
        remoteDataSource.createUser(userDto = user.toUserDto()).toAuthResponse()
}