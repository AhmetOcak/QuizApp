package com.quizapp.domain.usecase.quiz

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.quiz.CreateQuestionResponse
import com.quizapp.domain.model.quiz.QuestionBody
import com.quizapp.domain.repository.QuizRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateQuestionUseCase @Inject constructor(private val repository: QuizRepository) {

    suspend operator fun invoke(questionBody: QuestionBody, token: String) : Flow<Response<CreateQuestionResponse>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.createQuestion(questionBody, token)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("CreateQuestionUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("CreateQuestionUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("CreateQuestionUseCase.kt", e.stackTraceToString())
        }
    }
}