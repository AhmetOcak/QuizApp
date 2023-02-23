package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.quiz.QuizRemoteDataSource
import com.quizapp.data.mappers.*
import com.quizapp.domain.model.quiz.*
import com.quizapp.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val remoteDataSource: QuizRemoteDataSource
) : QuizRepository {

    override suspend fun getQuizList(quizzesQuery: QuizzesQuery): Quizzes =
        remoteDataSource.getQuizList(quizzesQueryDto = quizzesQuery.toQuizzesQueryDto()).toQuizzes()

    override suspend fun getQuizValues(quizId: String): QuizValues =
        remoteDataSource.getQuizValues(quizId = quizId).toQuizValues()

    override suspend fun createQuiz(createQuiz: CreateQuiz, token: String) =
        remoteDataSource.createQuiz(createQuizDto = createQuiz.toCreateQuizDto(), token = token)

    override suspend fun createQuestion(questionBody: QuestionBody, token: String) =
        remoteDataSource.createQuestion(questionBodyDto = questionBody.toQuestionBodyDto(), token = token)

    override suspend fun createOptions(optionsBody: OptionsBody, token: String) =
        remoteDataSource.createOptions(optionsBodyDto = optionsBody.toOptionsBodyDto(), token = token)

    override suspend fun getAllCategories(): Categories =
        remoteDataSource.getAllCategories().toCategories()
}