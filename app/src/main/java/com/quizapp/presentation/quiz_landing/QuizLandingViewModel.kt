package com.quizapp.presentation.quiz_landing

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.quizapp.core.navigation.NavNames
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizLandingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var quizId: String = savedStateHandle["quizId"] ?: "?"
        private set
    var quizTitle: String = savedStateHandle["quizTitle"] ?: "?"
        private set
    var quizDescription: String = savedStateHandle["quizDescription"] ?: "?"
        private set
    var quizAuthorUserName: String = savedStateHandle["quizAuthorUserName"] ?: "?"
        private set
    var quizCreatedDate: String = savedStateHandle["quizCreatedDate"] ?: "?"
        private set
    var quizAuthorUserImage: String = savedStateHandle["quizAuthorUserImage"] ?: "?"
        private set
    var categoryName: String = savedStateHandle["categoryName"] ?: "?"
        private set

    init {
        Log.e(
            "quiz data",
            "quizId: $quizId, quizTitle: $quizTitle, quizDescription: $quizDescription, " +
                    "quizAuthorUserName: $quizAuthorUserName, quizCreatedDate: $quizCreatedDate, " +
                    "quizAuthorUserImage: $quizAuthorUserImage, categoryName: $categoryName"
        )
    }

    fun navigateQuizScreen() = Navigator.navigate("${NavNames.quiz_screen}/${quizId}")

    fun navigateBackScreen() = Navigator.navigate(NavScreen.SearchScreen.route) {
        popUpTo(NavScreen.QuizLandingScreen.route) {
            inclusive = true
        }
    }
}