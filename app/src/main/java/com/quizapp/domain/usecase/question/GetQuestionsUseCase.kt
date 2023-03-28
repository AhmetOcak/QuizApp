package com.quizapp.domain.usecase.question

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.question.QuestionList
import com.quizapp.domain.repository.QuestionRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(private val repository: QuestionRepository) {

    suspend operator fun invoke(token: String, quizId: String) : Flow<Response<QuestionList>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getQuestions(token, quizId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetQuestionsUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetQuestionsUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetQuestionsUseCase.kt", e.stackTraceToString())
        }
    }
}