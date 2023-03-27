package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.question.QuestionRemoteDataSource
import com.quizapp.data.mappers.toQuestion
import com.quizapp.data.mappers.toQuestionBodyDto
import com.quizapp.domain.model.question.QuestionBody
import com.quizapp.domain.model.question.QuestionList
import com.quizapp.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(private val remoteDataSource: QuestionRemoteDataSource) : QuestionRepository {

    override suspend fun getQuestions(token: String, quizId: String): QuestionList =
        remoteDataSource.getQuestions(token = token, quizId = quizId).toQuestion()

    override suspend fun updateQuestion(token: String, body: QuestionBody) =
        remoteDataSource.updateQuestion(token = token, body = body.toQuestionBodyDto())
}