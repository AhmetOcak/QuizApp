package com.quizapp.domain.repository

import androidx.paging.PagingData
import com.quizapp.data.datasource.remote.quiz.entity.RecordsDto
import com.quizapp.domain.model.quiz.*
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    suspend fun getQuizList(quizzesQuery: QuizzesQuery) : Quizzes

    suspend fun getQuizValues(quizId: String) : QuizValues

    suspend fun createQuiz(createQuiz: CreateQuiz, token: String)

    suspend fun createQuestion(questionBody: QuestionBody, token: String)

    suspend fun createOptions(optionsBody: OptionsBody, token: String)

    suspend fun getAllCategories() : Categories

    fun searchQuiz(searchKeyword: String) : Flow<PagingData<RecordsDto>>
}