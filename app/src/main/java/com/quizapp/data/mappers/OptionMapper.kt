package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.option.entity.AnswerBodyDto
import com.quizapp.data.datasource.remote.option.entity.OptionsDto
import com.quizapp.data.datasource.remote.option.entity.UpdateOptionBodyDto
import com.quizapp.domain.model.option.AnswerBody
import com.quizapp.domain.model.option.Option
import com.quizapp.domain.model.option.Options
import com.quizapp.domain.model.option.UpdateOptionBody

fun OptionsDto.toOptions(): Options {
    return Options(
        options = options.map {
            Option(
                optionId = it.optionId,
                description = it.description,
                isAnswer = it.isAnswer
            )
        } as ArrayList<Option>
    )
}

fun UpdateOptionBody.toUpdateOptionBodyDto(): UpdateOptionBodyDto {
    return UpdateOptionBodyDto(
        id = id,
        description = description
    )
}

fun AnswerBody.toAnswerBodyDto(): AnswerBodyDto {
    return AnswerBodyDto(
        oldAnswerId = oldAnswerId,
        newAnswerId = newAnswerId
    )
}