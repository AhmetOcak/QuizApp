package com.quizapp.data.datasource.remote.category

import com.quizapp.data.datasource.remote.category.entity.CategoriesDto

interface CategoriesRemoteDataSource {

    suspend fun getCategories() : CategoriesDto
}