package com.quizapp.domain.usecase.quiz

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.quiz.Quizzes
import com.quizapp.domain.model.quiz.QuizzesQuery
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetQuizListUseCase @Inject constructor(private val quizRepository: QuizRepository) {

    suspend operator fun invoke(quizzesQuery: QuizzesQuery): Flow<Response<Quizzes>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = quizRepository.getQuizList(quizzesQuery)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("get quiz list error", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
                Log.e("get quiz list error", e.stackTraceToString())
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
                Log.e("get quiz list error", e.stackTraceToString())
            }
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("get quiz list error", e.stackTraceToString())
        }
    }
}