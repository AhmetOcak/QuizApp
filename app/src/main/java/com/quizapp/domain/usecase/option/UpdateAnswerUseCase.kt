package com.quizapp.domain.usecase.option

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.option.AnswerBody
import com.quizapp.domain.repository.OptionRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateAnswerUseCase @Inject constructor(private val repository: OptionRepository) {

    suspend operator fun invoke(token: String, body: AnswerBody): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.updateAnswer(token, body)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("update answer error", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
                Log.e("update answer error", e.stackTraceToString())
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
                Log.e("update answer error", e.stackTraceToString())
            }
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("update answer error", e.stackTraceToString())
        }
    }
}