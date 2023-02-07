package com.quizapp.data.datasource.remote.auth.api

import com.quizapp.data.datasource.remote.auth.entity.AuthResponseDto
import com.quizapp.data.datasource.remote.auth.entity.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/Users/Create")
    suspend fun createUser(@Body userDto: UserDto): AuthResponseDto
}