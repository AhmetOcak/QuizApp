package com.quizapp.presentation.create_quiz

import com.quizapp.domain.model.quiz.Categories

sealed interface CategoriesState {
    object Loading : CategoriesState
    data class Success(val data: Categories) : CategoriesState
    data class Error(val errorMessage: String) : CategoriesState
}