package com.quizapp.data.datasource.remote.quiz.api

import com.quizapp.data.datasource.remote.quiz.entity.*
import retrofit2.http.*

interface QuizApi {

    @GET("api/Quizzes/GetQuizList")
    suspend fun getQuizList(
        @Query("Page") page: Int,
        @Query("PageSize") pageSize: Int
    ): QuizzesDto

    @GET("api/Quizzes/GetQuizValuesFromDb")
    suspend fun getQuizValues(
        @Query("id") quizId: String
    ) : QuizValuesDto

    @POST("api/Quizzes/Create")
    suspend fun createQuiz(
        @Body createQuizDto: CreateQuizDto,
        @Header("Authorization") token: String
    )

    @POST("api/Questions/Create")
    suspend fun createQuestion(
        @Body questionBodyDto: QuestionBodyDto
    )

    @POST("api/Options/Create")
    suspend fun createOptions(
        @Body optionsBodyDto: OptionsBodyDto
    )

    @GET("api/Category/GetAll")
    suspend fun getAllCategories(): CategoriesDto

}