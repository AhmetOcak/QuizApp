package com.quizapp.data.datasource.remote.quiz

import com.quizapp.data.datasource.remote.quiz.entity.*

interface QuizRemoteDataSource {

    suspend fun getQuizList(quizzesQueryDto: QuizzesQueryDto) : QuizzesDto

    suspend fun getQuizValues(quizId: String) : QuizValuesDto

    suspend fun createQuiz(createQuizDto: CreateQuizDto, token: String)

    suspend fun createQuestion(questionBodyDto: QuestionBodyDto, token: String)

    suspend fun createOptions(optionsBodyDto: OptionsBodyDto, token: String)

    suspend fun getAllCategories() : CategoriesDto

    suspend fun searchQuiz(searchKeyword: String, page: Int) : SearchQuizResultDto
}