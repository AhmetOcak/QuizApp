package com.quizapp.data.datasource.remote.category.api

import com.quizapp.data.datasource.remote.category.entity.CategoriesDto
import retrofit2.http.GET

interface CategoryApi {

    @GET("api/Category/GetAll")
    suspend fun getCategories() : CategoriesDto
}