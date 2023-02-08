package com.quizapp.domain.usecase.auth

import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.auth.Login
import com.quizapp.domain.model.auth.LoginResponse
import com.quizapp.domain.repository.AuthRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(login: Login): Flow<Response<LoginResponse>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = authRepository.signIn(login = login)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
        }
    }
}