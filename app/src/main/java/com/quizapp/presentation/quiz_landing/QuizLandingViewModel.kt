package com.quizapp.presentation.quiz_landing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.quizapp.core.navigation.NavNames
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.core.navigation.QuizLandingScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private val args = QuizLandingScreenArgs

@HiltViewModel
class QuizLandingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var quizId: String = savedStateHandle[args.QUIZ_ID] ?: "?"
        private set
    var quizTitle: String = savedStateHandle[args.QUIZ_TITLE] ?: "?"
        private set
    var quizDescription: String = savedStateHandle[args.QUIZ_DESCR] ?: "?"
        private set
    var quizAuthorUserName: String = savedStateHandle[args.QUIZ_AUTHOR_NAME] ?: "?"
        private set
    var quizCreatedDate: String = savedStateHandle[args.QUIZ_CREATED_DATE] ?: "?"
        private set
    var quizAuthorUserImage: String = savedStateHandle[args.QUIZ_AUTHOR_IMG] ?: "?"
        private set
    var categoryName: String = savedStateHandle[args.QUIZ_CATEGORY] ?: "?"
        private set

    fun navigateQuizScreen() = Navigator.navigate("${NavNames.quiz_screen}/${quizId}")

    fun navigateBackScreen() = Navigator.navigate(NavScreen.SearchScreen.route) {
        popUpTo(NavScreen.QuizLandingScreen.route) {
            inclusive = true
        }
    }
}