package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.quiz.entity.OptionsDto
import com.quizapp.domain.model.quiz.Options

fun OptionsDto.toOptions() : Options {
    return Options(
        questionId = questionId,
        description = description,
        id = id,
        isAnswer = isAnswer,
        createdDate = createdDate
    )
}