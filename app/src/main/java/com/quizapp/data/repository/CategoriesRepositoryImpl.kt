package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.category.CategoriesRemoteDataSource
import com.quizapp.data.mappers.toCategories
import com.quizapp.domain.model.category.Categories
import com.quizapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val remoteDataSource: CategoriesRemoteDataSource) : CategoriesRepository {

    override suspend fun getCategories(): Categories =
        remoteDataSource.getCategories().toCategories()

}