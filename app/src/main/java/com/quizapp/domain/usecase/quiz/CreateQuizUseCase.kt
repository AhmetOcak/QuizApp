package com.quizapp.domain.usecase.quiz

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.quiz.CreateQuiz
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateQuizUseCase @Inject constructor(private val repository: QuizRepository) {

    suspend operator fun invoke(createQuiz: CreateQuiz, token: String): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.createQuiz(createQuiz = createQuiz, token = token)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("create quiz error", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
                Log.e("create quiz error", e.stackTraceToString())
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
                Log.e("create quiz error", e.stackTraceToString())
            }
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("create quiz error", e.stackTraceToString())
        }
    }
}