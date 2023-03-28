package com.quizapp.domain.repository

import com.quizapp.domain.model.category.Categories

interface CategoriesRepository {

    suspend fun getCategories() : Categories
}