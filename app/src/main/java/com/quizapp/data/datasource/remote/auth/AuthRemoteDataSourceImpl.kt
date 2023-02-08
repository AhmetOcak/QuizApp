package com.quizapp.data.datasource.remote.auth

import com.quizapp.data.datasource.remote.auth.api.AuthApi
import com.quizapp.data.datasource.remote.auth.entity.LoginDto
import com.quizapp.data.datasource.remote.auth.entity.LoginResponseBodyDto
import com.quizapp.data.datasource.remote.auth.entity.LoginResponseDto
import com.quizapp.data.datasource.remote.auth.entity.UserDto
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(private val api: AuthApi) : AuthRemoteDataSource {

    override suspend fun createUser(userDto: UserDto) = api.createUser(userDto = userDto)

    override suspend fun signIn(loginDto: LoginDto): LoginResponseDto = api.signIn(loginDto = loginDto)

}