package com.quizapp.domain.usecase.category

import android.util.Log
import com.quizapp.core.common.Response
import com.quizapp.core.common.getErrorMessage
import com.quizapp.domain.model.category.Categories
import com.quizapp.domain.repository.CategoriesRepository
import com.quizapp.domain.utils.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: CategoriesRepository) {

    suspend operator fun invoke() : Flow<Response<Categories>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getCategories()))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = Messages.INTERNET))
            Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
        } catch (e: HttpException) {
            val errorMessage = e.getErrorMessage()
            if (errorMessage != null) {
                emit(Response.Error(errorMessage = errorMessage))
                Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
            } else {
                emit(Response.Error(errorMessage = Messages.UNKNOWN))
                Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
            }
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: Messages.UNKNOWN))
            Log.e("GetCategoriesUseCase.kt", e.stackTraceToString())
        }
    }
}