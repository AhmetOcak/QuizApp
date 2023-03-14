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

fun StartQuizDto.toStartQuiz(): StartQuiz {
    return StartQuiz(
        startedQuiz = StartedQuiz(
            quizId = startedQuiz.quizId,
            title = startedQuiz.title,
            description = startedQuiz.description,
            categoryName = startedQuiz.categoryName,
            questions = startedQuiz.questions.map {
                StartedQuizQuestions(
                    questionId = it.questionId,
                    title = it.title,
                    description = it.description,
                    options = it.options.map { options ->
                        StartedQuizOptions(
                            optionId = options.optionId,
                            description = options.description
                        )
                    } as ArrayList<StartedQuizOptions>
                )
            } as ArrayList<StartedQuizQuestions>
        )
    )
}

fun FinishQuizBody.toFinishQuizDto(): FinishQuizBodyDto {
    return FinishQuizBodyDto(
        quiz = FinishedQuizBodyDto(
            quizId = quiz.quizId,
            title = quiz.title,
            description = quiz.description,
            questions = quiz.questions.map {
                AnswersBodyDto(
                    questionId = it.questionId,
                    title = it.title,
                    description = it.description,
                    selectedOptionId = it.selectedOptionId,
                    selectedOptionDescription = it.selectedOptionDescription
                )
            } as ArrayList<AnswersBodyDto>
        )
    )
}

fun FinishQuizResponseDto.toQuizResult(): QuizResult {
    return QuizResult(
        quizResult = QuizResultDetail(
            quizId = quizResult.quizId,
            title = quizResult.title,
            description = quizResult.description,
            correctAnswerCount = quizResult.correctAnswerCount,
            score = quizResult.score,
            questions = quizResult.questions.map {
                QuestionsResultDetail(
                    title = it.title,
                    description = it.description,
                    selectedOptionDescription = it.selectedOptionDescription,
                    isCorrect = it.isCorrect
                )
            } as ArrayList<QuestionsResultDetail>
        )
    )
}

fun UserQuizzesDto.toUserQuizzes(): UserQuizzes {
    return UserQuizzes(
        quizzes = quizzes.map {
            UserQuizzesDetail(
                quizId = it.quizId,
                title = it.title,
                userName = it.userName,
                description = it.description,
                categoryName = it.categoryName,
                quizCreatedDate = it.quizCreatedDate,
                userPhotoUrl = it.userPhotoUrl
            )
        } as ArrayList<UserQuizzesDetail>
    )
}