package com.quizapp.presentation.home

import com.quizapp.domain.model.category.Categories

sealed interface CategoriesState {
    object Loading : CategoriesState
    data class Success(val data: Categories) : CategoriesState
    data class Error(val errorMessage: String) : CategoriesState
}