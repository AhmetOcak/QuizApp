package com.quizapp.presentation.create_quiz

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizapp.core.common.Response
import com.quizapp.core.common.getToken
import com.quizapp.domain.model.quiz.CreateQuiz
import com.quizapp.domain.model.quiz.OpBody
import com.quizapp.domain.model.quiz.OptionsBody
import com.quizapp.domain.model.quiz.QuestionBody
import com.quizapp.domain.usecase.quiz.*
import com.quizapp.presentation.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor(
    private val createQuizUseCase: CreateQuizUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getQuizValuesUseCase: GetQuizValuesUseCase,
    private val createQuestionUseCase: CreateQuestionUseCase,
    private val createOptionsUseCase: CreateOptionsUseCase,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _createQuizState = MutableStateFlow<CreateQuizState>(CreateQuizState.Nothing)
    val createQuizState = _createQuizState.asStateFlow()

    private val _createQuizInputFieldState = MutableStateFlow<CreateQuizInputFieldState>(CreateQuizInputFieldState.Nothing)
    val createQuizInputFieldState = _createQuizInputFieldState.asStateFlow()

    private val _categoriesState = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val categoriesState = _categoriesState.asStateFlow()

    private val _createQuestionState = MutableStateFlow<CreateQuestionState>(CreateQuestionState.Nothing)
    val createQuestionState = _createQuestionState.asStateFlow()

    private val _createOptionsState =MutableStateFlow<CreateOptionsState>(CreateOptionsState.Loading)
    val createOptionsState = _createOptionsState.asStateFlow()

    private val _getQuizValuesState = MutableStateFlow<GetQuizValuesState>(GetQuizValuesState.Loading)
    val getQuizValuesState = _getQuizValuesState.asStateFlow()

    private val _createQuestionInputFieldState = MutableStateFlow<CreateQuestionInputFieldState>(CreateQuestionInputFieldState.Nothing)
    val createQuestionInputFieldState = _createQuestionInputFieldState.asStateFlow()

    var quizTitle by mutableStateOf("")
        private set
    var quizDescription by mutableStateOf("")
        private set
    var token by mutableStateOf("")
        private set

    var quizTitleError by mutableStateOf(false)
        private set
    var quizDescriptionError by mutableStateOf(false)
        private set

    var questionTitle by mutableStateOf("")
        private set
    var question by mutableStateOf("")
        private set
    var answerIndex by mutableStateOf(-1)
        private set

    var questionTitleError by mutableStateOf(false)
        private set
    var questionError by mutableStateOf(false)
        private set

    private var answer1 by mutableStateOf("")
    private var answer2 by mutableStateOf("")
    private var answer3 by mutableStateOf("")
    private var answer4 by mutableStateOf("")

    private var categoryId: String = ""
    private var quizId: String = "848aa68b-a70a-482a-befd-49fce1d44a38"
    private var questionId: String = ""
    private var options: ArrayList<OpBody> = arrayListOf()

    init {
        token = sharedPreferences.getToken() ?: ""
        getAllCategories()
        Log.e("token -> ", token)
    }

    fun setCategoryId(id: String){ categoryId = id }

    fun setTrueAnswer(index: Int) { answerIndex = index }

    fun updateQuizTitleField(newValue: String) { quizTitle = newValue }

    fun updateQuizDescriptionField(newValue: String) { quizDescription = newValue }

    fun updateQuestionTitleField(newValue: String) { questionTitle = newValue }

    fun updateQuestionField(newValue: String) { question = newValue }

    fun updateAnswersFields(newValue: String, index: Int) {
        when (index) {
            0 -> { answer1 = newValue }
            1 -> { answer2 = newValue }
            2 -> { answer3 = newValue }
            else -> { answer4 = newValue }
        }
    }

    fun setAnswersFieldsValue(index: Int): String {
        return when (index) {
            0 -> { answer1 }
            1 -> { answer2 }
            2 -> { answer3 }
            else -> { answer4 }
        }
    }

    private fun getAllCategories() = viewModelScope.launch(Dispatchers.IO) {
        getAllCategoriesUseCase().collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _categoriesState.value = CategoriesState.Loading
                    Log.e("categories", "loading")
                }
                is Response.Success -> {
                    _categoriesState.value = CategoriesState.Success(data = response.data)
                    Log.e("categories success", response.data.toString())
                }
                is Response.Error -> {
                    _categoriesState.value = CategoriesState.Error(errorMessage = response.errorMessage)
                    Log.e("categories error", response.errorMessage)
                }
            }
        }
    }

    fun createQuiz() = viewModelScope.launch(Dispatchers.IO) {
        if (checkQuizInputFields() && checkIsCategorySelected()) {
            createQuizUseCase(
                createQuiz = CreateQuiz(
                    title = quizTitle,
                    description = quizDescription,
                    categoryId = categoryId
                ),
                token = "Bearer $token"
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _createQuizState.value = CreateQuizState.Loading
                        Log.e("create quiz", "loading")
                    }
                    is Response.Success -> {
                        _createQuizState.value = CreateQuizState.Success
                        Log.e("create quiz", "success")
                    }
                    is Response.Error -> {
                        _createQuizState.value = CreateQuizState.Error(errorMessage = response.errorMessage)
                        Log.e("create quiz", "error")
                    }
                }
            }
        }
    }

    fun createQuestions() = viewModelScope.launch(Dispatchers.IO) {
        if (checkQuestionInputFields() && checkAnswersInputFields() && checkIsTrueAnswerAvailable()) {
            createQuestionUseCase(
                questionBody = QuestionBody(
                    quizId = quizId,
                    description = question,
                    title = questionTitle
                )
            ).collect() { response ->
                when (response) {
                    is Response.Loading -> {
                        _createQuestionState.value = CreateQuestionState.Loading
                        Log.e("create question", "loading")
                    }
                    is Response.Success -> {
                        _createQuestionState.value = CreateQuestionState.Success
                        Log.e("create question", "success")
                        getQuizValues()
                    }
                    is Response.Error -> {
                        _createQuestionState.value = CreateQuestionState.Error(errorMessage = response.errorMessage)
                        Log.e("create question", response.errorMessage)
                    }
                }
            }
        }
    }

    private fun getQuizValues() = viewModelScope.launch(Dispatchers.IO) {
        getQuizValuesUseCase(quizId = quizId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _getQuizValuesState.value = GetQuizValuesState.Loading
                    Log.e("get quiz values", "loading")
                }
                is Response.Success -> {
                    _getQuizValuesState.value = GetQuizValuesState.Success(data = response.data)
                    Log.e("get quiz values", "success")
                    questionId = response.data.questions.first { it.title == "soru5" }.id
                    options = setOptions()
                    createOptions()
                }
                is Response.Error -> {
                    _getQuizValuesState.value = GetQuizValuesState.Error(errorMessage = response.errorMessage)
                    Log.e("get quiz values", response.errorMessage)
                }
            }
        }
    }

    private fun createOptions() = viewModelScope.launch(Dispatchers.IO) {
        createOptionsUseCase(optionsBody = OptionsBody(options = options)).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _createOptionsState.value = CreateOptionsState.Loading
                    Log.e("create options", "loading")
                }
                is Response.Success -> {
                    _createOptionsState.value = CreateOptionsState.Success
                    Log.e("create options", "success")
                    resetQuestionInputs()
                }
                is Response.Error -> {
                    _createOptionsState.value = CreateOptionsState.Error(errorMessage = response.errorMessage)
                    Log.e("create options", response.errorMessage)
                }
            }
        }
    }

    private fun setOptions() : ArrayList<OpBody> = arrayListOf(
        OpBody(
            questionId = questionId,
            description = answer1,
            isAnswer = answerIndex == 0
        ),
        OpBody(
            questionId = questionId,
            description = answer2,
            isAnswer = answerIndex == 1
        ),
        OpBody(
            questionId = questionId,
            description = answer3,
            isAnswer = answerIndex == 2
        ),
        OpBody(
            questionId = questionId,
            description = answer4,
            isAnswer = answerIndex == 3
        ),
    )

    private fun checkQuizInputFields(): Boolean {
        return if (quizTitle.isBlank() && quizDescription.isBlank()) {
            _createQuizInputFieldState.value = CreateQuizInputFieldState.Error(errorMessage = Messages.FILL)
            quizTitleError = true
            quizDescriptionError = true
            false
        } else if (quizTitle.isBlank()) {
            _createQuizInputFieldState.value = CreateQuizInputFieldState.Error(errorMessage = Messages.FILL)
            quizTitleError = true
            quizDescriptionError = false
            false
        } else if (quizDescription.isBlank()) {
            _createQuizInputFieldState.value = CreateQuizInputFieldState.Error(errorMessage = Messages.FILL)
            quizTitleError = false
            quizDescriptionError = true
            false
        } else {
            _createQuizInputFieldState.value = CreateQuizInputFieldState.Nothing
            quizTitleError = false
            quizDescriptionError = false
            true
        }
    }

    private fun checkIsCategorySelected(): Boolean {
        return if (categoryId.isBlank()) {
            _createQuizInputFieldState.value = CreateQuizInputFieldState.Error(errorMessage = Messages.SELECT_CATEGORY)
            false
        } else {
            _createQuizInputFieldState.value = CreateQuizInputFieldState.Nothing
            true
        }
    }

    private fun checkQuestionInputFields(): Boolean {
        return if(question.isBlank() && questionTitle.isBlank()) {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Error(errorMessage = Messages.FILL)
            questionError = true
            questionTitleError = true
            false
        } else if (question.isBlank()) {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Error(errorMessage = Messages.FILL)
            questionError = true
            quizTitleError = false
            false
        } else if (questionTitle.isBlank()) {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Error(errorMessage = Messages.FILL)
            questionError = false
            questionTitleError = true
            false
        } else {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Nothing
            questionError = false
            questionTitleError = false
            true
        }
    }

    private fun checkAnswersInputFields(): Boolean {
        return if (answer1.isBlank() || answer2.isBlank() || answer3.isBlank() || answer4.isBlank()) {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Error(errorMessage = Messages.FILL_ANSWERS)
            false
        } else {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Nothing
            true
        }
    }

    private fun checkIsTrueAnswerAvailable(): Boolean {
        return if (answerIndex == -1) {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Error(errorMessage = Messages.SELECT_TRUE_ANSWER)
            false
        } else {
            _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Nothing
            true
        }
    }

    fun resetCreateQuizInFieState() {
        _createQuizInputFieldState.value = CreateQuizInputFieldState.Nothing
    }

    fun resetCreateQuizState() {
        _createQuizState.value = CreateQuizState.Nothing
    }

    fun resetCreateQuestionState() {
        _createQuestionState.value = CreateQuestionState.Nothing
    }

    fun resetCreateQuestionInpFieState() {
        _createQuestionInputFieldState.value = CreateQuestionInputFieldState.Nothing
    }

    private fun resetQuestionInputs() {
        questionTitle = ""
        question = ""
        answer1 = ""
        answer2 = ""
        answer3 = ""
        answer4 = ""
    }

}