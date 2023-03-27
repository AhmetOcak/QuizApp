package com.quizapp.presentation.update_quiz

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.core.navigation.UpdateQuizScreenArgs
import com.quizapp.domain.model.option.AnswerBody
import com.quizapp.domain.model.option.UpdateOptionBody
import com.quizapp.domain.model.question.QuestionBody
import com.quizapp.domain.model.question.QuestionList
import com.quizapp.domain.model.quiz.UpdateQuizBody
import com.quizapp.domain.usecase.option.GetOptionsUseCase
import com.quizapp.domain.usecase.option.UpdateAnswerUseCase
import com.quizapp.domain.usecase.option.UpdateOptionUseCase
import com.quizapp.domain.usecase.question.GetQuestionsUseCase
import com.quizapp.domain.usecase.question.UpdateQuestionUseCase
import com.quizapp.domain.usecase.quiz.UpdateQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class UpdateSection {
    TITLE_DESCR,
    QUESTIONS
}

enum class QuestionBottomSheet {
    QUESTION_TITLE,
    QUESTION_DESCR,
    QUESTION_OPTION
}

@HiltViewModel
class UpdateQuizViewModel @Inject constructor(
    private val updateQuizUseCase: UpdateQuizUseCase,
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val getOptionsUseCase: GetOptionsUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase,
    private val updateOptionUseCase: UpdateOptionUseCase,
    private val updateAnswerUseCase: UpdateAnswerUseCase,
    sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var token: String = ""
    private var quizId: String = ""
    private var questionData: QuestionList = QuestionList(arrayListOf())

    private var questionCount: Int = 0

    var oldAnswerId by mutableStateOf("")
        private set
    var newAnswerId by mutableStateOf("")
        private set

    var currentSection by mutableStateOf(UpdateSection.TITLE_DESCR)
        private set

    var isAnswerClicked by mutableStateOf(false)
        private set

    // for the show user
    var quizTitle by mutableStateOf("")
        private set
    var quizDescr by mutableStateOf("")
        private set

    // text field value
    var quizTitleBottomSheet by mutableStateOf("")
        private set
    var quizDescrBottomSheet by mutableStateOf("")
        private set

    // text field value
    var questTitleBottomSheet by mutableStateOf("")
        private set
    var questDescrBottomSheet by mutableStateOf("")
        private set

    var optionBottomSheet by mutableStateOf("")
        private set

    var selectedOptionId by mutableStateOf("")
        private set

    var isQuizTitleSelected by mutableStateOf(true)
        private set

    var questBottomSheet by mutableStateOf(QuestionBottomSheet.QUESTION_TITLE)
        private set

    var questionIndex by mutableStateOf(0)
        private set

    private val _updateQuizState = MutableStateFlow<UpdateQuizState>(UpdateQuizState.Nothing)
    val updateQuizState = _updateQuizState.asStateFlow()

    private val _getQuestionsState = MutableStateFlow<GetQuestionsState>(GetQuestionsState.Loading)
    val getQuestionState = _getQuestionsState.asStateFlow()

    private val _getOptionsState = MutableStateFlow<GetOptionsState>(GetOptionsState.Loading)
    val getOptionsState = _getOptionsState.asStateFlow()

    private val _updateQuestionState = MutableStateFlow<UpdateQuestionState>(UpdateQuestionState.Nothing)
    val updateQuestionState = _updateQuestionState.asStateFlow()

    private val _updateOptionState = MutableStateFlow<UpdateOptionState>(UpdateOptionState.Nothing)
    val updateOptionsState = _updateOptionState.asStateFlow()

    private val _updateAnswerState = MutableStateFlow<UpdateAnswerState>(UpdateAnswerState.Nothing)
    val updateAnswerState = _updateAnswerState.asStateFlow()

    init {
        token = sharedPreferences.getToken() ?: ""
        quizId = savedStateHandle[UpdateQuizScreenArgs.ID] ?: ""

        quizTitle = savedStateHandle[UpdateQuizScreenArgs.TITLE] ?: ""
        quizDescr = savedStateHandle[UpdateQuizScreenArgs.DESCR] ?: ""

        quizTitleBottomSheet = quizTitle
        quizDescrBottomSheet = quizDescr
    }

    fun setIsQuizTitleSelected(newValue: Boolean) { isQuizTitleSelected = newValue }

    fun setQuestionBottomSheet(newValue: QuestionBottomSheet) { questBottomSheet = newValue }

    fun updateQuizTitleBottomSheet(newValue: String) { quizTitleBottomSheet = newValue }

    fun updateQuizDescrBottomSheet(newValue: String) { quizDescrBottomSheet = newValue }

    fun updateQuestTitleBottomSheet(newValue: String) { questTitleBottomSheet = newValue }

    fun updateQuestDescrBottomSheet(newValue: String) { questDescrBottomSheet = newValue }

    fun setQuestValBottomSheet() {
        if (questionData.questionList.isNotEmpty()) {
            questTitleBottomSheet = questionData.questionList[questionIndex].title
            questDescrBottomSheet = questionData.questionList[questionIndex].description
        }
    }

    fun setSection(newSection: UpdateSection) { currentSection = newSection }

    fun setIsAnswerClicked(newValue: Boolean) { isAnswerClicked = newValue }

    fun updateOptionBottomSheet(newValue: String) { optionBottomSheet = newValue }

    fun setSelectedOption(id: String, descr: String) {
        selectedOptionId = id
        optionBottomSheet = descr
    }

    fun setOldAnswer(value: String) { oldAnswerId = value }

    fun setNewAnswer(value: String) { newAnswerId = value }

    fun incQuestionIndex() {
        if (questionIndex < questionCount - 1 && questionIndex != questionCount - 1) {
            questionIndex++
        }
    }

    fun decQuestionIndex() {
        if (questionIndex > 0) {
            questionIndex--
        } else {
            currentSection = UpdateSection.TITLE_DESCR
        }
    }

    fun updateQuiz() = viewModelScope.launch(Dispatchers.IO) {
        updateQuizUseCase(
            token = "Bearer $token",
            body = UpdateQuizBody(
                id = quizId,
                title = quizTitleBottomSheet,
                description = quizDescrBottomSheet
            )
        ).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _updateQuizState.value = UpdateQuizState.Loading
                }
                is Response.Success -> {
                    quizTitle = quizTitleBottomSheet
                    quizDescr = quizDescrBottomSheet

                    _updateQuizState.value = UpdateQuizState.Success
                }
                is Response.Error -> {
                    _updateQuizState.value = UpdateQuizState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun getQuestions() = viewModelScope.launch(Dispatchers.IO) {
        getQuestionsUseCase(token = "Bearer $token", quizId = quizId).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _getQuestionsState.value = GetQuestionsState.Loading
                }
                is Response.Success -> {
                    _getQuestionsState.value = GetQuestionsState.Success(data = response.data)

                    questionCount = response.data.questionList.size
                    questionData = response.data

                    // first question values
                    if (questTitleBottomSheet.isBlank() && questDescrBottomSheet.isBlank()) {
                        questTitleBottomSheet = response.data.questionList[0].title
                        questDescrBottomSheet = response.data.questionList[0].description
                    }

                    getOptions()
                }
                is Response.Error -> {
                    _getQuestionsState.value = GetQuestionsState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun getOptions() = viewModelScope.launch(Dispatchers.IO) {
        getOptionsUseCase(token = "Bearer $token", questionId = questionData.questionList[questionIndex].questionId).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _getOptionsState.value = GetOptionsState.Loading
                }
                is Response.Success -> {
                    _getOptionsState.value = GetOptionsState.Success(data = response.data)
                }
                is Response.Error -> {
                    _getOptionsState.value = GetOptionsState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun updateQuestion() = viewModelScope.launch(Dispatchers.IO) {
        updateQuestionUseCase(
            token = "Bearer $token",
            body = QuestionBody(
                id = questionData.questionList[questionIndex].questionId,
                title = questTitleBottomSheet.ifBlank { questionData.questionList[questionIndex].title },
                description = questDescrBottomSheet.ifBlank { questionData.questionList[questionIndex].description }
            )
        ).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _updateQuestionState.value = UpdateQuestionState.Loading
                }
                is Response.Success -> {
                    _updateQuestionState.value = UpdateQuestionState.Success
                }
                is Response.Error -> {
                    _updateQuestionState.value = UpdateQuestionState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun updateOption(body: UpdateOptionBody) = viewModelScope.launch(Dispatchers.IO) {
        updateOptionUseCase(token = "Bearer $token", body = body).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _updateOptionState.value = UpdateOptionState.Loading
                }
                is Response.Success -> {
                    _updateOptionState.value = UpdateOptionState.Success

                    getOptions()
                }
                is Response.Error -> {
                    _updateOptionState.value = UpdateOptionState.Error(errorMessage = response.errorMessage)
                }
            }

        }
    }

    fun updateAnswer() = viewModelScope.launch(Dispatchers.IO) {
        updateAnswerUseCase(
            token = "Bearer $token",
            body = AnswerBody(oldAnswerId, newAnswerId)
        ).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _updateAnswerState.value = UpdateAnswerState.Loading
                }
                is Response.Success -> {
                    _updateAnswerState.value = UpdateAnswerState.Success
                    getOptions()

                    oldAnswerId = ""
                    newAnswerId = ""
                }
                is Response.Error -> {
                    _updateAnswerState.value = UpdateAnswerState.Error(errorMessage = response.errorMessage)
                }
            }
        }
    }

    fun resetUpdateQuizState() { _updateQuizState.value = UpdateQuizState.Nothing }

    fun resetGetQuestionsState() { _getQuestionsState.value = GetQuestionsState.Loading }

    fun resetUpdateQuestionState() { _updateQuestionState.value = UpdateQuestionState.Nothing }

    fun resetUpdateOptionState() { _updateOptionState.value = UpdateOptionState.Nothing }

    fun resetUpdateAnswerState() { _updateAnswerState.value = UpdateAnswerState.Nothing }
}