package com.quizapp.data.datasource.remote.question

import com.quizapp.data.datasource.remote.question.api.QuestionApi
import com.quizapp.data.datasource.remote.question.entity.QuestionBodyDto
import com.quizapp.data.datasource.remote.question.entity.QuestionListDto
import javax.inject.Inject

class QuestionRemoteDataSourceImpl @Inject constructor(private val api: QuestionApi) : QuestionRemoteDataSource {

    override suspend fun getQuestions(token: String, quizId: String): QuestionListDto =
        api.getQuestions(token = token, quizId = quizId)

    override suspend fun updateQuestion(token: String, body: QuestionBodyDto) =
        api.updateQuestion(token = token, body = body)
}