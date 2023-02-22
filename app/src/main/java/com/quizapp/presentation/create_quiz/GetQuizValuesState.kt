package com.quizapp.presentation.create_quiz

import com.quizapp.domain.model.quiz.QuizValues

sealed interface GetQuizValuesState {
    object Loading : GetQuizValuesState
    data class Success(val data: QuizValues) : GetQuizValuesState
    data class Error(val errorMessage: String) : GetQuizValuesState
}