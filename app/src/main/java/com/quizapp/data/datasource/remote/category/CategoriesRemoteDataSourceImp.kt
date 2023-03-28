package com.quizapp.data.datasource.remote.category

import com.quizapp.data.datasource.remote.category.api.CategoryApi
import com.quizapp.data.datasource.remote.category.entity.CategoriesDto
import javax.inject.Inject

class CategoriesRemoteDataSourceImp @Inject constructor(private val api: CategoryApi) : CategoriesRemoteDataSource {

    override suspend fun getCategories(): CategoriesDto = api.getCategories()
}