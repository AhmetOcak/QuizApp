package com.quizapp.domain.usecase.reset_password

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.reset_password.ResetPassword
import com.quizapp.domain.model.reset_password.ResetPasswordBody
import com.quizapp.domain.repository.ResetPasswordRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val repository: ResetPasswordRepository) {

    suspend operator fun invoke(resetPassword: ResetPassword, resetPasswordBody: ResetPasswordBody): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.resetPassword(resetPassword, resetPasswordBody)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("ResetPasswordUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("ResetPasswordUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("ResetPasswordUseCase.kt", e.stackTraceToString())
        }
    }
}