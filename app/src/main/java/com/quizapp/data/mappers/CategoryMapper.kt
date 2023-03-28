package com.quizapp.data.mappers

import com.quizapp.data.datasource.remote.category.entity.CategoriesDto
import com.quizapp.domain.model.category.Categories
import com.quizapp.domain.model.category.Category

fun CategoriesDto.toCategories(): Categories {
    return Categories(
        categories = categories.map {
            Category(
                id = it.id,
                categoryName = it.categoryName
            )
        } as ArrayList<Category>
    )
}