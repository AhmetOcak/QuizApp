package com.quizapp.presentation.quiz

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.ui.component.CircleCheckbox
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.CustomSlider
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.core.ui.theme.*
import com.quizapp.domain.model.quiz.*

private val gradientColors = listOf(
    LightPurple,
    LightPink,
    StrangeRed,
    StrangeOrange,
    LightBrown,
    LightYellow
)

@Composable
fun QuizScreen(modifier: Modifier = Modifier, viewModel: QuizViewModel = hiltViewModel()) {

    val startQuizState by viewModel.startQuizState.collectAsState()

    var selectedOptionId by rememberSaveable { mutableStateOf("") }

    QuizScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        startQuizState = startQuizState,
        questionIndex = viewModel.questionIndex,
        onChecked = { selectedOptionId = it },
        onPreviousClicked = {
            selectedOptionId = ""
            viewModel.goPreviousQuestion()
        },
        onNextClicked = {
            if (selectedOptionId.isNotBlank()) {
                viewModel.addSelectedAnswer(
                    answer = AnswersBody(
                        questionId = it.questions[viewModel.questionIndex].questionId,
                        title = it.questions[viewModel.questionIndex].title,
                        description = it.questions[viewModel.questionIndex].description,
                        selectedOptionId = it.questions[viewModel.questionIndex].options.find { it2 ->
                            it2.optionId == selectedOptionId
                        }?.optionId!!,
                        selectedOptionDescription = it.questions[viewModel.questionIndex].options.find { it2 ->
                            it2.optionId == selectedOptionId
                        }?.description!!
                    )
                )
                selectedOptionId = ""
            }
            viewModel.goNextQuestion()
        },
        selectedOptionId = selectedOptionId
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun QuizScreenContent(
    modifier: Modifier,
    viewModel: QuizViewModel,
    startQuizState: StartQuizState,
    questionIndex: Int,
    onChecked: (String) -> Unit,
    onPreviousClicked: () -> Unit,
    onNextClicked: (StartedQuiz) -> Unit,
    selectedOptionId: String
) {
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            when (startQuizState) {
                is StartQuizState.Loading -> {
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CustomLoadingSpinner()
                    }
                }
                is StartQuizState.Success -> {
                    QuizSection(
                        modifier = modifier,
                        questionIndex = questionIndex,
                        quizData = startQuizState.data,
                        viewModel = viewModel,
                        selectedOptionId = selectedOptionId,
                        onChecked = onChecked,
                        onPreviousClicked = onPreviousClicked,
                        onNextClicked = onNextClicked
                    )
                }
                is StartQuizState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        startQuizState.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

@Composable
private fun QuizSection(
    modifier: Modifier,
    questionIndex: Int,
    quizData: StartQuiz,
    viewModel: QuizViewModel,
    selectedOptionId: String,
    onChecked: (String) -> Unit,
    onPreviousClicked: () -> Unit,
    onNextClicked: (StartedQuiz) -> Unit
) {
    TimerSection(modifier = modifier)
    QuestionSection(
        modifier = modifier,
        questionTitle = quizData.startedQuiz.questions[questionIndex].title,
        questionDescription = quizData.startedQuiz.questions[questionIndex].description
    )
    AnswersSection(
        modifier = modifier,
        options = quizData.startedQuiz.questions[questionIndex].options,
        onChecked = onChecked,
        checked = {
            if (selectedOptionId.isBlank()) {
                return@AnswersSection viewModel.returnSelectedOptionId(quizData.startedQuiz.questions[questionIndex].questionId) == it
            } else return@AnswersSection selectedOptionId == it
        }
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutBtnCustom(
            modifier = modifier.weight(1f),
            onClick = onPreviousClicked,
            buttonText = "Previous",
            enabled = !viewModel.isQuestionIndexZero()
        )
        OutBtnCustom(
            modifier = modifier.weight(1f),
            onClick = {
                if (viewModel.isQuestionIndexLast()) {
                    onNextClicked(quizData.startedQuiz)
                    viewModel.finishQuiz()
                } else {
                    onNextClicked(quizData.startedQuiz)
                }
            },
            buttonText = if (viewModel.isQuestionIndexLast()) "Finish" else "Next"
        )
    }
}

@Composable
private fun TimerSection(modifier: Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CustomSlider(
                modifier = modifier.fillMaxWidth(),
                trackHeight = 42.dp,
                onValueChange = { value -> },
                value = 50f,
                inactiveTrackColor = Color.Transparent,
                borderStroke = BorderStroke(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(colors = gradientColors)
                )
            )
            Text(
                text = "50",
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primaryVariant
                )
            )
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_access_time),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        }
    }
}

@Composable
private fun QuestionSection(
    modifier: Modifier,
    questionTitle: String,
    questionDescription: String
) {
    Column {
        QuestionTitle(modifier = modifier, questionTitle = questionTitle)
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 2.dp,
            color = Color.Gray
        )
        Question(modifier = modifier, questionDescription = questionDescription)
    }
}

@Composable
private fun QuestionTitle(modifier: Modifier, questionTitle: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        text = questionTitle,
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.SemiBold),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun Question(modifier: Modifier, questionDescription: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = questionDescription,
        style = MaterialTheme.typography.h2.copy(color = Color.Black, fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun AnswersSection(
    modifier: Modifier,
    options: ArrayList<StartedQuizOptions>,
    onChecked: (String) -> Unit,
    checked: (String) -> Boolean
) {
    Column(
        modifier = modifier.padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        options.forEach {
            Answer(
                modifier = modifier.padding(vertical = 8.dp),
                answerText = it.description,
                onChecked = { onChecked(it.optionId) },
                checked = checked(it.optionId)
            )
        }
    }
}

@Composable
private fun Answer(
    modifier: Modifier,
    answerText: String,
    onChecked: () -> Unit,
    checked: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TextFieldDefaults.MinHeight + 8.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Black),
                shape = RoundedCornerShape(25)
            )
            .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(25))
            .clickable(enabled = true, onClick = {}),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier.padding(start = 16.dp),
            text = answerText,
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primaryVariant)
        )
        CircleCheckbox(
            modifier = modifier,
            onChecked = onChecked,
            selected = checked
        )
    }
}