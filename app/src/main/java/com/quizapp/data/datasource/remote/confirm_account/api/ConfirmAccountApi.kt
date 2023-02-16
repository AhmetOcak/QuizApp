package com.quizapp.data.datasource.remote.confirm_account.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ConfirmAccountApi {

    @GET("api/Auth/ConfirmMail")
    suspend fun confirmMail(
        @Query("email") email: String,
        @Query("token") token: String,
    )
}