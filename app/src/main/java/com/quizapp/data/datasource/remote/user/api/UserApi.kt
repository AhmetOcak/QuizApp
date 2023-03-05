package com.quizapp.data.datasource.remote.user.api

import com.quizapp.data.datasource.remote.user.entity.UpdatePasswordBodyDto
import com.quizapp.data.datasource.remote.user.entity.UpdateProfileBodyDto
import com.quizapp.data.datasource.remote.user.entity.UserProfileDto
import retrofit2.http.*

interface UserApi {

    @GET("api/Users/GetUserProfile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileDto

    @PUT("api/Users/UpdatePassword")
    suspend fun updatePassword(
        @Header("Authorization") token: String,
        @Body updatePasswordBodyDto: UpdatePasswordBodyDto
    )

    @PUT("api/Users/UpdateProfile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateProfileBodyDto: UpdateProfileBodyDto
    )

}