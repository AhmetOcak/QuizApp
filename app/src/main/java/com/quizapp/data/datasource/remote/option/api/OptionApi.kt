package com.quizapp.data.datasource.remote.option.api

import com.quizapp.data.datasource.remote.option.entity.AnswerBodyDto
import com.quizapp.data.datasource.remote.option.entity.OptionsDto
import com.quizapp.data.datasource.remote.option.entity.UpdateOptionBodyDto
import retrofit2.http.*

interface OptionApi {

    @GET("api/Options/GetOptionListOwner")
    suspend fun getOptions(
        @Header("Authorization") token: String,
        @Query("questionId") questionId: String
    ) : OptionsDto

    @PUT("api/Options/Update")
    suspend fun updateOption(
        @Header("Authorization") token: String,
        @Body body: UpdateOptionBodyDto
    )

    @PUT("api/Options/UpdateAnswer")
    suspend fun updateAnswer(
        @Header("Authorization") token: String,
        @Body body: AnswerBodyDto
    )
}