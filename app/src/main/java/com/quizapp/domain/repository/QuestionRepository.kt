package com.quizapp.domain.repository

import com.quizapp.domain.model.question.QuestionBody
import com.quizapp.domain.model.question.QuestionList

interface QuestionRepository {

    suspend fun getQuestions(token: String, quizId: String) : QuestionList

    suspend fun updateQuestion(token: String, body: QuestionBody)
}