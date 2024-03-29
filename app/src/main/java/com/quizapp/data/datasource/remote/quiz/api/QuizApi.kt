package com.quizapp.data.datasource.remote.quiz.api

import com.quizapp.data.datasource.remote.quiz.entity.*
import retrofit2.http.*

interface QuizApi {

    @GET("api/Quizzes/GetQuizList")
    suspend fun getQuizList(
        @Query("Page") page: Int,
        @Query("PageSize") pageSize: Int
    ): QuizzesDto

    @POST("api/Quizzes/Create")
    suspend fun createQuiz(
        @Body createQuizDto: CreateQuizDto,
        @Header("Authorization") token: String
    ) : CreateQuizResponseDto

    @POST("api/Questions/Create")
    suspend fun createQuestion(
        @Body questionBodyDto: QuestionBodyDto,
        @Header("Authorization") token: String
    ) : CreateQuestionResponseDto

    @POST("api/Options/Create")
    suspend fun createOptions(
        @Body optionsBodyDto: OptionsBodyDto,
        @Header("Authorization") token: String
    )

    @GET("api/Category/GetAll")
    suspend fun getAllCategories(): CategoriesDto

    @GET("api/Quizzes/SearchQuiz")
    suspend fun searchQuiz(
        @Query("search") search: String,
        @Query("Page") page: Int,
        //@Query("PageSize") pageSize: Int = 10
    ): SearchQuizResultDto

    @POST("api/Quizzes/StartQuiz")
    suspend fun startQuiz(
        @Query("quizId") quizId: String,
        @Header("Authorization") token: String
    ): StartQuizDto

    @POST("api/Quizzes/FinishQuiz")
    suspend fun finishQuiz(
        @Body answers: FinishQuizBodyDto,
        @Header("Authorization") token: String
    ): FinishQuizResponseDto

    @GET("api/Quizzes/GetUserQuizzes")
    suspend fun getUserQuizzes(
        @Header("Authorization") token: String
    ): UserQuizzesDto

    @PUT("api/Quizzes/Update")
    suspend fun updateQuiz(
        @Header("Authorization") token: String,
        @Body body: UpdateQuizBodyDto
    )
}