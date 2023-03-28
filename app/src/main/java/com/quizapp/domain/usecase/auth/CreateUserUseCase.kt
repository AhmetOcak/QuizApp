package com.quizapp.domain.usecase.auth

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.auth.User
import com.quizapp.domain.repository.AuthRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(user: User): Flow<Response<String>> = flow {
        try {
            emit(Response.Loading)
            emit(
                Response.Success(
                    data = authRepository.createUser(user = user).message ?: Messages.USER_CRE_SUC
                )
            )
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("CreateUserUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("CreateUserUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("CreateUserUseCase.kt", e.stackTraceToString())
        }
    }
}