package com.quizapp.domain.repository

import com.quizapp.domain.model.quiz.*

interface QuizRepository {

    suspend fun getQuizList(quizzesQuery: QuizzesQuery) : Quizzes

    suspend fun getQuizValues(quizId: String) : QuizValues

    suspend fun createQuiz(createQuiz: CreateQuiz, token: String)

    suspend fun createQuestion(questionBody: QuestionBody, token: String)

    suspend fun createOptions(optionsBody: OptionsBody, token: String)

    suspend fun getAllCategories() : Categories
}