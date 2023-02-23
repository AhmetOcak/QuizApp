package com.quizapp.data.datasource.remote.user.api

import com.quizapp.data.datasource.remote.user.entity.UserProfileDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("api/Users/GetUserProfile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileDto
}