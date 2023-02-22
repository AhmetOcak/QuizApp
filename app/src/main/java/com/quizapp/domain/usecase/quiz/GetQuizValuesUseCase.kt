package com.quizapp.domain.usecase.quiz

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.quiz.QuizValues
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetQuizValuesUseCase @Inject constructor(private val repository: QuizRepository) {

    suspend operator fun invoke(quizId: String) : Flow<Response<QuizValues>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getQuizValues(quizId = quizId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("get quiz values error", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
                Log.e("get quiz values error", e.stackTraceToString())
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
                Log.e("get quiz values error", e.stackTraceToString())
            }
        } catch (e: Exception) {
            Log.e("interesting error", e.stackTraceToString())
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("get quiz values error", e.stackTraceToString())
        }
    }
}