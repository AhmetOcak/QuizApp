package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.question.entity.QuestionBodyDto
import com.quizapp.data.datasource.remote.question.entity.QuestionListDto
import com.quizapp.domain.model.question.Option
import com.quizapp.domain.model.question.Question
import com.quizapp.domain.model.question.QuestionBody
import com.quizapp.domain.model.question.QuestionList

fun QuestionListDto.toQuestion(): QuestionList {
    return QuestionList(
        questionList = questionList.map {
            Question(
                questionId = it.questionId,
                title = it.title,
                description = it.description,
                options = it.options.map { option ->
                    Option(
                        optionId = option.optionId,
                        description = option.description
                    )
                } as ArrayList<Option>
            )
        } as ArrayList<Question>
    )
}

fun QuestionBody.toQuestionBodyDto() : QuestionBodyDto {
    return QuestionBodyDto(
        id = id,
        title = title,
        description = description
    )
}