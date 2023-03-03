package com.quizapp.domain.repository

import androidx.paging.PagingData
import com.quizapp.data.datasource.remote.quiz.entity.RecordsDto
import com.quizapp.domain.model.quiz.*
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    suspend fun getQuizList(quizzesQuery: QuizzesQuery) : Quizzes

    suspend fun createQuiz(createQuiz: CreateQuiz, token: String) : CreateQuizResponse

    suspend fun createQuestion(questionBody: QuestionBody, token: String) : CreateQuestionResponse

    suspend fun createOptions(optionsBody: OptionsBody, token: String)

    suspend fun getAllCategories() : Categories

    fun searchQuiz(searchKeyword: String) : Flow<PagingData<RecordsDto>>

    suspend fun startQuiz(quizId: String, token: String) : StartQuiz

    suspend fun finishQuiz(answers: FinishQuizBody, token: String) : QuizResult
}