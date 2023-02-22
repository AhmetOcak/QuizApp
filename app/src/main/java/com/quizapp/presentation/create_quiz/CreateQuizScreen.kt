package com.quizapp.presentation.create_quiz

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.presentation.utils.Messages

@Composable
fun CreateQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateQuizViewModel = hiltViewModel()
) {
    val createQuizState by viewModel.createQuizState.collectAsState()
    val createQuizInputFieldState by viewModel.createQuizInputFieldState.collectAsState()
    val categoriesState by viewModel.categoriesState.collectAsState()
    val createQuestionState by viewModel.createQuestionState.collectAsState()
    val createOptionsState by viewModel.createOptionsState.collectAsState()
    val getQuizValuesState by viewModel.getQuizValuesState.collectAsState()
    val createQuestionInputFieldState by viewModel.createQuestionInputFieldState.collectAsState()

    var selectedCategory by remember { mutableStateOf(-1) }
    var isTrueAnswer by remember { mutableStateOf(-1) }

    CreateQuizScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        createQuizState = createQuizState,
        createQuizInputFieldState = createQuizInputFieldState,
        categoriesState = categoriesState,
        selectedCategory = selectedCategory,
        onClick = { index, categoryId ->
            selectedCategory = index
            viewModel.setCategoryId(id = categoryId)
        },
        isTrueAnswer = isTrueAnswer,
        onAnswerClick = {
            isTrueAnswer = it
            viewModel.setTrueAnswer(it)
        },
        answer = { viewModel.setAnswersFieldsValue(index = it) },
        answerValueChanged = { value, index ->
            viewModel.updateAnswersFields(newValue = value, index = index)
        },
        questionTitle = viewModel.questionTitle,
        onQuestionTitleChanged = { viewModel.updateQuestionTitleField(it) },
        question = viewModel.question,
        onQuestionChanged = { viewModel.updateQuestionField(it) },
        onCreateQuestionClick = { viewModel.createQuestions() },
        createQuestionState = createQuestionState,
        onResetQuestionState = { viewModel.resetCreateQuestionState() },
        createOptionsState = createOptionsState,
        getQuizValuesState = getQuizValuesState,
        resetCreateQuestionState = {
            viewModel.resetCreateQuestionState()
            isTrueAnswer = -1
        },
        createQuestionInputFieldState = createQuestionInputFieldState,
        onResetState = { viewModel.resetCreateQuestionInpFieState() },
        questionError = viewModel.questionError,
        questionTitleError = viewModel.questionTitleError
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun CreateQuizScreenContent(
    modifier: Modifier,
    viewModel: CreateQuizViewModel,
    createQuizState: CreateQuizState,
    createQuizInputFieldState: CreateQuizInputFieldState,
    categoriesState: CategoriesState,
    selectedCategory: Int,
    onClick: (Int, String) -> Unit,
    isTrueAnswer: Int,
    onAnswerClick: (Int) -> Unit,
    answer: (Int) -> String,
    answerValueChanged: (String, Int) -> Unit,
    questionTitle: String,
    onQuestionTitleChanged: (String) -> Unit,
    question: String,
    onQuestionChanged: (String) -> Unit,
    onCreateQuestionClick: () -> Unit,
    createQuestionState: CreateQuestionState,
    onResetQuestionState: () -> Unit,
    getQuizValuesState: GetQuizValuesState,
    createOptionsState: CreateOptionsState,
    resetCreateQuestionState: () -> Unit,
    onResetState: () -> Unit,
    createQuestionInputFieldState: CreateQuestionInputFieldState,
    questionError: Boolean,
    questionTitleError: Boolean
) {
    Scaffold(modifier = modifier, topBar = { TitleSection(modifier = modifier) }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 32.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            when (createQuizState) {
                is CreateQuizState.Nothing -> {
                    CategoriesSection(
                        modifier = modifier,
                        selectedCategory = selectedCategory,
                        onClick = onClick,
                        categoriesState = categoriesState
                    )
                    QuizContentSection(modifier = modifier, viewModel = viewModel)
                    CreateQuizButtonSection(
                        modifier = modifier,
                        onClick = { viewModel.createQuiz() }
                    )
                }
                is CreateQuizState.Loading -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CustomLoadingSpinner()
                    }
                }
                is CreateQuizState.Success -> {
                    ShowMessage(message = Messages.CREATE_QUIZ_SUCCESS)
                    CreateQuestionSection(
                        modifier = modifier,
                        isTrueAnswer = isTrueAnswer,
                        answerValueChanged = answerValueChanged,
                        answer = answer,
                        onAnswerClick = onAnswerClick,
                        questionTitle = questionTitle,
                        onQuestionTitleChanged = onQuestionTitleChanged,
                        question = question,
                        onQuestionChanged = onQuestionChanged,
                        onCreateQuestionClick = onCreateQuestionClick,
                        createQuestionState = createQuestionState,
                        onResetQuestionState = onResetQuestionState,
                        createOptionsState = createOptionsState,
                        getQuizValuesState = getQuizValuesState,
                        resetCreateQuestionState = resetCreateQuestionState,
                        createQuestionInputFieldState = createQuestionInputFieldState,
                        onResetState = onResetState,
                        questionError = questionError,
                        questionTitleError = questionTitleError
                    )
                }
                is CreateQuizState.Error -> {
                    ShowMessage(
                        message = createQuizState.errorMessage,
                        createQuizState = createQuizState,
                        onResetCreateQuizState = { viewModel.resetCreateQuizState() }
                    )
                }
            }
            ShowCreateQuizInputFieldErrors(
                createQuizInputFieldState = createQuizInputFieldState,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun TitleSection(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = "Create Quiz",
        style = MaterialTheme.typography.h1.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.primaryVariant
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CategoriesSection(
    modifier: Modifier,
    selectedCategory: Int,
    onClick: (Int, String) -> Unit,
    categoriesState: CategoriesState
) {
    when (categoriesState) {
        is CategoriesState.Loading -> {
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CustomLoadingSpinner()
            }
        }
        is CategoriesState.Success -> {
            Column(modifier = modifier.fillMaxWidth()) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Choose Category",
                    style = MaterialTheme.typography.h2.copy(color = MaterialTheme.colors.primaryVariant)
                )
                LazyRow(
                    modifier = modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(categoriesState.data.categories) { index, item ->
                        Category(
                            modifier = modifier,
                            name = item.categoryName,
                            image = R.drawable.categorie_football,
                            selectedCategory = selectedCategory,
                            index = index,
                            onClick = onClick,
                            categoryId = item.id
                        )
                    }
                }
            }
        }
        is CategoriesState.Error -> {

        }
    }
}

@Composable
private fun QuizContentSection(modifier: Modifier, viewModel: CreateQuizViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        QuizInput(
            modifier = modifier,
            title = "Quiz Title",
            onValueChanged = { viewModel.updateQuizTitleField(it) },
            value = viewModel.quizTitle,
            placeHolderText = "Title",
            isError = viewModel.quizTitleError
        )
        QuizInput(
            modifier = modifier,
            title = "Quiz Description",
            onValueChanged = { viewModel.updateQuizDescriptionField(it) },
            value = viewModel.quizDescription,
            placeHolderText = "Description",
            isError = viewModel.quizDescriptionError
        )
    }
}

@Composable
private fun QuizInput(
    modifier: Modifier,
    title: String,
    onValueChanged: (String) -> Unit,
    value: String,
    placeHolderText: String,
    isError: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        Subtitle(modifier = modifier, title = title)
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = onValueChanged,
            placeHolderText = placeHolderText,
            value = value,
            isError = isError
        )
    }
}

@Composable
private fun CreateQuizButtonSection(modifier: Modifier, onClick: () -> Unit) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = onClick,
        buttonText = "Create Quiz"
    )
}

@Composable
private fun ShowCreateQuizInputFieldErrors(
    createQuizInputFieldState: CreateQuizInputFieldState,
    viewModel: CreateQuizViewModel
) {
    when (createQuizInputFieldState) {
        is CreateQuizInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                createQuizInputFieldState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetCreateQuizInFieState()
        }
        is CreateQuizInputFieldState.Nothing -> {}
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Category(
    modifier: Modifier,
    name: String,
    image: Int,
    index: Int,
    selectedCategory: Int,
    onClick: (Int, String) -> Unit,
    categoryId: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = modifier.size(width = 160.dp, height = 112.dp),
            onClick = {
                onClick(index, categoryId)
            },
            border = BorderStroke(
                width = 1.dp,
                color = if (index == selectedCategory) Color.Green else Color.White
            )
        ) {
            Image(
                modifier = modifier.fillMaxSize(),
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = if (index == selectedCategory) null else ColorFilter.colorMatrix(
                    ColorMatrix().apply {
                        setToSaturation(0f)
                    })
            )
        }
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = name,
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
    }
}

@Composable
private fun CreateQuestionSection(
    modifier: Modifier,
    isTrueAnswer: Int,
    answerValueChanged: (String, Int) -> Unit,
    answer: (Int) -> String,
    onAnswerClick: (Int) -> Unit,
    questionTitle: String,
    onQuestionTitleChanged: (String) -> Unit,
    question: String,
    onQuestionChanged: (String) -> Unit,
    onCreateQuestionClick: () -> Unit,
    createQuestionState: CreateQuestionState,
    onResetQuestionState: () -> Unit,
    getQuizValuesState: GetQuizValuesState,
    createOptionsState: CreateOptionsState,
    resetCreateQuestionState: () -> Unit,
    createQuestionInputFieldState: CreateQuestionInputFieldState,
    onResetState: () -> Unit,
    questionError: Boolean,
    questionTitleError: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        when (createQuestionState) {
            is CreateQuestionState.Nothing -> {
                QuestionContent(
                    modifier = modifier,
                    onQuestionChanged = onQuestionChanged,
                    question = question,
                    onQuestionTitleChanged = onQuestionTitleChanged,
                    questionTitle = questionTitle,
                    questionError = questionError,
                    questionTitleError = questionTitleError
                )
                AnswersContent(
                    modifier = modifier,
                    answerValueChanged = answerValueChanged,
                    answer = answer,
                    onAnswerClick = onAnswerClick,
                    isTrueAnswer = isTrueAnswer
                )
                OutBtnCustom(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    onClick = onCreateQuestionClick,
                    buttonText = "Create Question"
                )
            }
            is CreateQuestionState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is CreateQuestionState.Success -> {
                QuizValuesContent(
                    modifier = modifier,
                    getQuizValuesState = getQuizValuesState,
                    createOptionsState = createOptionsState,
                    resetCreateQuestionState = resetCreateQuestionState
                )
            }
            is CreateQuestionState.Error -> {
                ShowMessage(
                    message = createQuestionState.errorMessage,
                    createQuestionState = createQuestionState,
                    onResetQuestionState = onResetQuestionState
                )
            }
        }
        ShowCreateQuestionInputFieldErrors(
            createQuestionInputFieldState = createQuestionInputFieldState,
            onResetState = onResetState
        )
    }
}

@Composable
private fun QuizValuesContent(
    modifier: Modifier,
    getQuizValuesState: GetQuizValuesState,
    createOptionsState: CreateOptionsState,
    resetCreateQuestionState: () -> Unit
) {
    when (getQuizValuesState) {
        is GetQuizValuesState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CustomLoadingSpinner()
            }
        }
        is GetQuizValuesState.Success -> {
            CreateOptionsContent(
                modifier = modifier,
                createOptionsState = createOptionsState,
                resetCreateQuestionState = resetCreateQuestionState
            )
        }
        is GetQuizValuesState.Error -> {
            ShowMessage(message = getQuizValuesState.errorMessage)
        }
    }
}

@Composable
private fun CreateOptionsContent(
    modifier: Modifier,
    createOptionsState: CreateOptionsState,
    resetCreateQuestionState: () -> Unit
) {
    when (createOptionsState) {
        is CreateOptionsState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CustomLoadingSpinner()
            }
        }
        is CreateOptionsState.Success -> {
            ShowMessage(message = Messages.CREATE_QUEST_SUCCESS)
            resetCreateQuestionState()
        }
        is CreateOptionsState.Error -> {
            ShowMessage(message = createOptionsState.errorMessage)
        }
    }
}

@Composable
private fun QuestionContent(
    modifier: Modifier,
    onQuestionChanged: (String) -> Unit,
    question: String,
    onQuestionTitleChanged: (String) -> Unit,
    questionTitle: String,
    questionTitleError: Boolean,
    questionError: Boolean
) {
    Subtitle(modifier = modifier, title = "Question Title")
    OtfCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onValueChanged = onQuestionTitleChanged,
        placeHolderText = "Title",
        value = questionTitle,
        isError = questionTitleError
    )
    Subtitle(modifier = modifier.padding(top = 16.dp), title = "Enter Your Question")
    OtfCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onValueChanged = onQuestionChanged,
        placeHolderText = "Question",
        value = question,
        isError = questionError
    )
}

@Composable
private fun AnswersContent(
    modifier: Modifier,
    answer: (Int) -> String,
    isTrueAnswer: Int,
    answerValueChanged: (String, Int) -> Unit,
    onAnswerClick: (Int) -> Unit
) {
    Subtitle(
        modifier = modifier.padding(top = 32.dp), title = "Enter Answers"
    )
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(4) { index ->
            Answer(
                modifier = modifier,
                answer = answer,
                isTrueAnswer = isTrueAnswer,
                index = index,
                answerValueChanged = answerValueChanged,
                onAnswerClick = onAnswerClick
            )
        }
    }
}

@Composable
private fun Answer(
    modifier: Modifier,
    answer: (Int) -> String,
    isTrueAnswer: Int,
    index: Int,
    answerValueChanged: (String, Int) -> Unit,
    onAnswerClick: (Int) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = modifier.weight(5f)) {
            OtfCustom(
                modifier = modifier.fillMaxWidth(),
                onValueChanged = { answerValueChanged(it, index) },
                placeHolderText = "Answer",
                value = answer(index)
            )
        }
        IconButton(modifier = modifier.weight(1f), onClick = { onAnswerClick(index) }) {
            Icon(
                modifier = modifier.size(32.dp),
                painter = painterResource(id = R.drawable.ic_baseline_check),
                contentDescription = null,
                tint = if (index == isTrueAnswer) Color.Green else Color.Gray
            )
        }
    }
}

@Composable
private fun ShowMessage(
    message: String,
    createQuizState: CreateQuizState = CreateQuizState.Nothing,
    createQuestionState: CreateQuestionState = CreateQuestionState.Nothing,
    onResetCreateQuizState: () -> Unit = {},
    onResetQuestionState: () -> Unit = {}
) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
    if (createQuizState is CreateQuizState.Error) {
        onResetCreateQuizState()
    }
    if (createQuestionState is CreateQuestionState.Error) {
        onResetQuestionState()
    }
}

@Composable
private fun Subtitle(title: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.h2.copy(color = MaterialTheme.colors.primaryVariant)
    )
}

@Composable
private fun ShowCreateQuestionInputFieldErrors(
    createQuestionInputFieldState: CreateQuestionInputFieldState,
    onResetState: () -> Unit
) {
    when (createQuestionInputFieldState) {
        is CreateQuestionInputFieldState.Error -> {
            Toast.makeText(
                LocalContext.current,
                createQuestionInputFieldState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            onResetState()
        }
        is CreateQuestionInputFieldState.Nothing -> {}
    }
}
