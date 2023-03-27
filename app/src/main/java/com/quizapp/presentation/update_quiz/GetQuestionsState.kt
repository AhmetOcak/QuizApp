package com.quizapp.presentation.update_quiz

import com.quizapp.domain.model.question.QuestionList

sealed interface GetQuestionsState {
    object Loading : GetQuestionsState
    data class Success(val data: QuestionList) : GetQuestionsState
    data class Error(val errorMessage: String) : GetQuestionsState
}