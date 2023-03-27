package com.quizapp.data.datasource.remote.option

import com.quizapp.data.datasource.remote.option.api.OptionApi
import com.quizapp.data.datasource.remote.option.entity.AnswerBodyDto
import com.quizapp.data.datasource.remote.option.entity.OptionsDto
import com.quizapp.data.datasource.remote.option.entity.UpdateOptionBodyDto
import javax.inject.Inject

class OptionRemoteDataSourceImpl @Inject constructor(private val api: OptionApi) : OptionRemoteDataSource {

    override suspend fun getOptions(token: String, questionId: String): OptionsDto =
        api.getOptions(token = token, questionId = questionId)

    override suspend fun updateOption(token: String, body: UpdateOptionBodyDto) =
        api.updateOption(token = token, body = body)

    override suspend fun updateAnswer(token: String, body: AnswerBodyDto) =
        api.updateAnswer(token = token, body = body)

}