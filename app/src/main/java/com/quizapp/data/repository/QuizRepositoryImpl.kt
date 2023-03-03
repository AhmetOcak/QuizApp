package com.quizapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.quizapp.data.datasource.remote.quiz.QuizRemoteDataSource
import com.quizapp.data.datasource.remote.quiz.SearchResultsPagingSource
import com.quizapp.data.datasource.remote.quiz.entity.RecordsDto
import com.quizapp.data.mappers.*
import com.quizapp.domain.model.quiz.*
import com.quizapp.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val remoteDataSource: QuizRemoteDataSource
) : QuizRepository {

    override suspend fun getQuizList(quizzesQuery: QuizzesQuery): Quizzes =
        remoteDataSource.getQuizList(quizzesQueryDto = quizzesQuery.toQuizzesQueryDto()).toQuizzes()

    override suspend fun createQuiz(createQuiz: CreateQuiz, token: String) =
        remoteDataSource.createQuiz(createQuizDto = createQuiz.toCreateQuizDto(), token = token).toCreateQuizResponse()

    override suspend fun createQuestion(questionBody: QuestionBody, token: String) =
        remoteDataSource.createQuestion(questionBodyDto = questionBody.toQuestionBodyDto(), token = token).toCreateQuestionResponse()

    override suspend fun createOptions(optionsBody: OptionsBody, token: String) =
        remoteDataSource.createOptions(optionsBodyDto = optionsBody.toOptionsBodyDto(), token = token)

    override suspend fun getAllCategories(): Categories =
        remoteDataSource.getAllCategories().toCategories()

    override fun searchQuiz(searchKeyword: String): Flow<PagingData<RecordsDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SearchResultsPagingSource(remoteDataSource, searchKeyword)
            }
        ).flow
    }

    override suspend fun startQuiz(quizId: String, token: String): StartQuiz =
        remoteDataSource.startQuiz(quizId = quizId, token = token).toStartQuiz()

    override suspend fun finishQuiz(answers: FinishQuizBody, token: String) =
        remoteDataSource.finishQuiz(answers = answers.toFinishQuizDto(), token = token).toQuizResult()
}