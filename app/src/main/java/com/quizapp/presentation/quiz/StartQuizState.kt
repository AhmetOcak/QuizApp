package com.quizapp.presentation.quiz

import com.quizapp.domain.model.quiz.StartQuiz

sealed interface StartQuizState {
    object Loading : StartQuizState
    data class Success(val data: StartQuiz) : StartQuizState
    data class Error(val errorMessage: String) : StartQuizState
}