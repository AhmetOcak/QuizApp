package com.quizapp.data.datasource.remote.question.api

import com.quizapp.data.datasource.remote.question.entity.QuestionBodyDto
import com.quizapp.data.datasource.remote.question.entity.QuestionListDto
import retrofit2.http.*

interface QuestionApi {

    @GET("api/Questions/GetQuestionList")
    suspend fun getQuestions(
        @Header("Authorization") token: String,
        @Query("quizId") quizId: String
    ): QuestionListDto

    @PUT("api/Questions/Update")
    suspend fun updateQuestion(
        @Header("Authorization") token: String,
        @Body body: QuestionBodyDto
    )
}