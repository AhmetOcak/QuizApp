package com.quizapp.presentation.quiz_result

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.quizapp.R
import com.quizapp.core.common.*
import com.quizapp.core.navigation.QuizResultScreenArgs
import com.quizapp.domain.model.quiz.QuizResult
import com.quizapp.presentation.utils.QuizResultMessages
import com.quizapp.presentation.utils.QuizScoreMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.NullPointerException
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class QuizResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    lateinit var quizResult: QuizResult
        private set

    var score by mutableStateOf(0)
        private set
    var userName by mutableStateOf("")
        private set

    var spentHour by mutableStateOf(0)
        private set
    var spentMinute by mutableStateOf(0)
        private set
    var spentSeconds by mutableStateOf(0)
        private set

    private var quizStartHour = savedStateHandle[QuizResultScreenArgs.QUIZ_START_HOUR] ?: 0
    private var quizStartMinute = savedStateHandle[QuizResultScreenArgs.QUIZ_START_MIN] ?: 0
    private var quizStartSeconds = savedStateHandle[QuizResultScreenArgs.QUIZ_START_SEC] ?: 0

    init {
        spentHour = getHour() - quizStartHour
        spentMinute = getMinute() - quizStartMinute
        spentSeconds = getSeconds() - quizStartSeconds

        val token = sharedPreferences.getToken()
        userName = token?.let { getUserNameFromToken(it) } ?: "-"
    }

    fun getQuizResult(quizResult: QuizResult?) {
        try {
            if (quizResult != null) {
                this.quizResult = quizResult
            } else {
                throw NullPointerException()
            }
        } catch (e: NullPointerException) {
            Log.e("quiz result error", e.stackTraceToString())
        } catch (e: Exception) {
            Log.e("quiz result error", e.stackTraceToString())
        }
    }

    fun setQuizResult() { score = quizResult.quizResult.score }

    fun setQuizResultImage(): Int {
        return if (score < 40) {
            R.drawable.bad_quiz_result
        } else if (score in 40..79) {
            R.drawable.middle_quiz_result
        } else {
            R.drawable.good_quiz_result
        }
    }

    fun setQuizResultMessage(): String {
        return if (score < 40) {
            QuizResultMessages.BAD
        } else if (score in 40..79) {
            QuizResultMessages.NOT_BAD
        } else {
            QuizResultMessages.CONGRATULATIONS
        }
    }

    fun setQuizScoreMessage(): String {
        return if (score < 40) {
            QuizScoreMessages.BAD_MESSAGE
        } else if (score in 40..79) {
            QuizScoreMessages.NOT_BAD_MESSAGE
        } else {
            QuizScoreMessages.CONGRATULATIONS_MESSAGE
        }
    }
}