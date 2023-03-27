package com.quizapp.domain.repository

import com.quizapp.domain.model.option.AnswerBody
import com.quizapp.domain.model.option.Options
import com.quizapp.domain.model.option.UpdateOptionBody

interface OptionRepository {

    suspend fun getOptions(token: String, questionId: String) : Options

    suspend fun updateOption(token: String, body: UpdateOptionBody)

    suspend fun updateAnswer(token: String, body: AnswerBody)
}