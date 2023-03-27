package com.quizapp.data.datasource.remote.option

import com.quizapp.data.datasource.remote.option.entity.AnswerBodyDto
import com.quizapp.data.datasource.remote.option.entity.OptionsDto
import com.quizapp.data.datasource.remote.option.entity.UpdateOptionBodyDto

interface OptionRemoteDataSource {

    suspend fun getOptions(token: String, questionId: String) : OptionsDto

    suspend fun updateOption(token: String, body: UpdateOptionBodyDto)

    suspend fun updateAnswer(token: String, body: AnswerBodyDto)
}