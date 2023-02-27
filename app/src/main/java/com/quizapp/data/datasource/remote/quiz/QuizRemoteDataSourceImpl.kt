package com.quizapp.data.datasource.remote.quiz

import com.quizapp.data.datasource.remote.quiz.api.QuizApi
import com.quizapp.data.datasource.remote.quiz.entity.*
import javax.inject.Inject

class QuizRemoteDataSourceImpl @Inject constructor(private val api: QuizApi) : QuizRemoteDataSource {

    override suspend fun getQuizList(quizzesQueryDto: QuizzesQueryDto): QuizzesDto =
        api.getQuizList(page = quizzesQueryDto.page, pageSize = quizzesQueryDto.pageSize)

    override suspend fun getQuizValues(quizId: String): QuizValuesDto =
        api.getQuizValues(quizId = quizId)

    override suspend fun createQuiz(createQuizDto: CreateQuizDto, token: String) =
        api.createQuiz(createQuizDto = createQuizDto, token = token)

    override suspend fun createQuestion(questionBodyDto: QuestionBodyDto, token: String) =
        api.createQuestion(questionBodyDto = questionBodyDto, token = token)

    override suspend fun createOptions(optionsBodyDto: OptionsBodyDto, token: String) =
        api.createOptions(optionsBodyDto = optionsBodyDto, token = token)

    override suspend fun getAllCategories(): CategoriesDto = api.getAllCategories()

    override suspend fun searchQuiz(searchKeyword: String, page: Int): SearchQuizResultDto =
        api.searchQuiz(
            search = searchKeyword,
            page = page
        )

}