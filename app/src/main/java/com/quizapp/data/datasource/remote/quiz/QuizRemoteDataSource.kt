package com.quizapp.data.datasource.remote.quiz

import com.quizapp.data.datasource.remote.quiz.entity.*

interface QuizRemoteDataSource {

    suspend fun getQuizList(quizzesQueryDto: QuizzesQueryDto) : QuizzesDto

    suspend fun createQuiz(createQuizDto: CreateQuizDto, token: String) : CreateQuizResponseDto

    suspend fun createQuestion(questionBodyDto: QuestionBodyDto, token: String) : CreateQuestionResponseDto

    suspend fun createOptions(optionsBodyDto: OptionsBodyDto, token: String)

    suspend fun getAllCategories() : CategoriesDto

    suspend fun searchQuiz(searchKeyword: String, page: Int) : SearchQuizResultDto

    suspend fun startQuiz(quizId: String, token: String) : StartQuizDto

    suspend fun finishQuiz(answers: FinishQuizBodyDto, token: String) : FinishQuizResponseDto

    suspend fun getUserQuizzes(token: String) : UserQuizzesDto
}