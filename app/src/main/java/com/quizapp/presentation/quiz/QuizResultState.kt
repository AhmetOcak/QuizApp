package com.quizapp.presentation.quiz

import com.quizapp.domain.model.quiz.QuizResult

sealed interface QuizResultState {
    object Nothing : QuizResultState
    object Loading : QuizResultState
    data class Success(val data: QuizResult) : QuizResultState
    data class Error(val errorMessage: String) : QuizResultState
}