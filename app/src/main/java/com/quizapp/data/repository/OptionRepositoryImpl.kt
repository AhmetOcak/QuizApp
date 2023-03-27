package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.option.OptionRemoteDataSource
import com.quizapp.data.mappers.toAnswerBodyDto
import com.quizapp.data.mappers.toOptions
import com.quizapp.data.mappers.toUpdateOptionBodyDto
import com.quizapp.domain.model.option.AnswerBody
import com.quizapp.domain.model.option.Options
import com.quizapp.domain.model.option.UpdateOptionBody
import com.quizapp.domain.repository.OptionRepository
import javax.inject.Inject

class OptionRepositoryImpl @Inject constructor(private val remoteDataSource: OptionRemoteDataSource) : OptionRepository {

    override suspend fun getOptions(token: String, questionId: String): Options =
        remoteDataSource.getOptions(token = token, questionId = questionId).toOptions()

    override suspend fun updateOption(token: String, body: UpdateOptionBody) =
        remoteDataSource.updateOption(token = token, body = body.toUpdateOptionBodyDto())

    override suspend fun updateAnswer(token: String, body: AnswerBody) =
        remoteDataSource.updateAnswer(token = token, body = body.toAnswerBodyDto())
}