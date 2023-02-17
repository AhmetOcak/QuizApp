package com.quizapp.data.datasource.remote.auth.api

import com.quizapp.data.datasource.remote.auth.entity.*
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/Users/Create")
    suspend fun createUser(@Body userDto: UserDto): AuthResponseDto

    @POST("api/Auth/Login")
    suspend fun signIn(@Body loginDto: LoginDto): LoginResponseDto
}