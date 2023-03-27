package com.quizapp.data.datasource.remote.question

import com.quizapp.data.datasource.remote.question.entity.QuestionBodyDto
import com.quizapp.data.datasource.remote.question.entity.QuestionListDto

interface QuestionRemoteDataSource {

    suspend fun getQuestions(token: String, quizId: String) : QuestionListDto

    suspend fun updateQuestion(token: String, body: QuestionBodyDto)
}