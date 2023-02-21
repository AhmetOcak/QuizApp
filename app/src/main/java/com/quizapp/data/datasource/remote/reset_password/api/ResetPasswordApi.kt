package com.quizapp.data.datasource.remote.reset_password.api

import com.quizapp.data.datasource.remote.reset_password.entity.ResetPasswordBodyDto
import retrofit2.http.*

interface ResetPasswordApi {

    @GET("api/Auth/PasswordResetMail")
    suspend fun sendPasswordResetMail(
        @Query("email") email: String
    )

    @POST("api/Auth/ResetPassword")
    suspend fun resetPassword(
        @Query("email") email: String,
        @Query("token") token: String,
        @Body newPassword: ResetPasswordBodyDto
    )
}