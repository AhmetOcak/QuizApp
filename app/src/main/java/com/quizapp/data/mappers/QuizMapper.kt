package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.quiz.entity.*
import com.quizapp.domain.model.quiz.*

fun CreateQuiz.toCreateQuizDto(): CreateQuizDto {
    return CreateQuizDto(
        description = description,
        title = title,
        categoryId = categoryId
    )
}

fun CategoriesDto.toCategories(): Categories {
    return Categories(
        categories = categories.map {
            Category(id = it.id, categoryName = it.categoryName)
        } as ArrayList<Category>
    )
}

fun QuizzesDto.toQuizzes(): Quizzes {
    return Quizzes(
        quizzes = QuizzesInfo(
            page = quizzes.page,
            pageSize = quizzes.pageSize,
            totalPages = quizzes.totalPages,
            records = quizzes.records.map {
                Quiz(
                    quizId = it.quizId,
                    title = it.title,
                    description = it.description
                )
            } as ArrayList<Quiz>
        )
    )
}

fun QuizzesQuery.toQuizzesQueryDto(): QuizzesQueryDto {
    return QuizzesQueryDto(
        page = page,
        pageSize = pageSize
    )
}

fun QuestionBody.toQuestionBodyDto() : QuestionBodyDto {
    return QuestionBodyDto(
        quizId = quizId,
        title = title,
        description = description
    )
}

fun OptionsBody.toOptionsBodyDto() : OptionsBodyDto {
    return OptionsBodyDto(
        questionId = questionId,
        options = options.map {
            OpBodyDto(
                description = it.description,
                isAnswer = it.isAnswer
            )
        } as ArrayList<OpBodyDto>
    )
}

/*fun SearchQuizResultDto.toSearchResults(): SearchQuizResults {
    return SearchQuizResults(
        results = Results(
            page = results.page,
            pageSize = results.pageSize,
            totalPages = results.totalPages,
            records = results.records.map {
                Records(
                    quizId = it.quizId,
                    title = it.title,
                    description = it.description
                )
            } as ArrayList<Records>
        )
    )
}*/

fun CreateQuizResponseDto.toCreateQuizResponse(): CreateQuizResponse {
    return CreateQuizResponse(
        message = message,
        quizId = quizId
    )
}

fun CreateQuestionResponseDto.toCreateQuestionResponse(): CreateQuestionResponse {
    return CreateQuestionResponse(
        message = message,
        questionId = questionId
    )
}