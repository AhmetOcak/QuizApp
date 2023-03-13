package com.quizapp.presentation.quiz

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.*
import com.quizapp.core.navigation.NavNames
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.domain.model.quiz.AnswersBody
import com.quizapp.domain.model.quiz.FinishQuizBody
import com.quizapp.domain.model.quiz.FinishedBodyQuiz
import com.quizapp.domain.usecase.quiz.FinishQuizUseCase
import com.quizapp.domain.usecase.quiz.StartQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val startQuizUseCase: StartQuizUseCase,
    private val finishQuizUseCase: FinishQuizUseCase,
    savedStateHandle: SavedStateHandle,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _startQuizState = MutableStateFlow<StartQuizState>(StartQuizState.Loading)
    val startQuizState = _startQuizState.asStateFlow()

    private val _quizResultState = MutableStateFlow<QuizResultState>(QuizResultState.Nothing)
    val quizResultState = _quizResultState.asStateFlow()

    private val quizId: String? = savedStateHandle["quizId"]
    private var token: String? = null

    private var quizTitle: String = ""
    private var quizDescription: String = ""

    private var answers: ArrayList<AnswersBody> = arrayListOf()

    private var quizStartHour: Int = 0
    private var quizStartMinute: Int = 0
    private var quizStartSeconds: Int = 0

    var questionIndex by mutableStateOf(0)
        private set
    var questionCount by mutableStateOf(-1)
        private set

    init {
        Log.e("quiz screen", quizId.toString())
        token = sharedPreferences.getToken()
        startQuiz(token = token)

        quizStartHour = getHour()
        quizStartMinute = getMinute()
        quizStartSeconds = getSeconds()
    }

    fun goNextQuestion() {
        if (!isQuestionIndexLast()) {
            questionIndex++
        }
    }

    fun goPreviousQuestion() { questionIndex-- }

    fun isQuestionIndexZero(): Boolean = questionIndex == 0

    fun isQuestionIndexLast(): Boolean = questionIndex == questionCount - 1

    fun addSelectedAnswer(answer: AnswersBody) {
        if (checkIsSelectedQuestionAnswerAdded(answer.questionId).isBlank()) {
            answers.add(answer)
        } else {
            updateSelectedAnswer(
                answer = answer,
                index = answers.indexOfFirst { it.questionId == answer.questionId }
            )
        }

        Log.e("selected options", answers.toString())
    }

    private fun updateSelectedAnswer(answer: AnswersBody, index: Int) {
        answers.set(index = index, answer)
    }

    private fun checkIsSelectedQuestionAnswerAdded(questionId: String): String {
        return answers.find { it.questionId == questionId }?.questionId ?: ""
    }

    fun returnSelectedOptionId(questionId: String): String {
        return answers.find { it.questionId == questionId }?.selectedOptionId ?: ""
    }

    private fun startQuiz(token: String?) = viewModelScope.launch(Dispatchers.IO) {
        if (quizId != null && token != null) {
            startQuizUseCase(
                quizId = quizId,
                token = "Bearer $token"
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _startQuizState.value = StartQuizState.Loading
                    }
                    is Response.Success -> {
                        _startQuizState.value = StartQuizState.Success(data = response.data)
                        questionCount = response.data.startedQuiz.questions.size
                        quizTitle = response.data.startedQuiz.title
                        quizDescription = response.data.startedQuiz.description
                        Log.e("question count", questionCount.toString())
                        Log.e("quiz data", response.data.toString())
                    }
                    is Response.Error -> {
                        _startQuizState.value = StartQuizState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        } else {
            StartQuizState.Error(errorMessage = "Something went wrong. Please try again later.")
        }
    }

    fun finishQuiz() = viewModelScope.launch(Dispatchers.IO) {
        if (quizId != null && token != null) {
            finishQuizUseCase(
                answers = FinishQuizBody(
                    quiz = FinishedBodyQuiz(
                        quizId = quizId,
                        title = quizTitle,
                        description = quizDescription,
                        questions = answers
                    )
                ),
                token = "Bearer $token"
            ).collect() { response ->
                when(response) {
                    is Response.Loading -> {
                        _quizResultState.value = QuizResultState.Loading
                    }
                    is Response.Success -> {
                        _quizResultState.value = QuizResultState.Success(data = response.data)
                        Navigator.navigate("${NavNames.quiz_result_screen}/${response.data.toString()}/${quizStartHour}/${quizStartMinute}/${quizStartSeconds}")
                        Log.e("quiz result", response.data.toString())
                    }
                    is Response.Error -> {
                        _quizResultState.value = QuizResultState.Error(errorMessage = response.errorMessage)
                    }
                }
            }
        }
    }
}