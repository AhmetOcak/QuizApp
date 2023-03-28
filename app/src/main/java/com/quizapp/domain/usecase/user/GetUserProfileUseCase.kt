package com.quizapp.domain.usecase.user

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.user.UserProfile
import com.quizapp.domain.repository.UserRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(token: String) : Flow<Response<UserProfile>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getUserProfile(token)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetUserProfileUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
            }
            Log.e("GetUserProfileUseCase.kt", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetUserProfileUseCase.kt", e.stackTraceToString())
        }
    }
}