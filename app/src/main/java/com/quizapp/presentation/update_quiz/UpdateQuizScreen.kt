package com.quizapp.presentation.update_quiz

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.ui.component.*
import com.quizapp.core.ui.theme.FailRed
import com.quizapp.core.ui.theme.LightYellow
import com.quizapp.core.ui.theme.SuccessGreen
import com.quizapp.domain.model.option.UpdateOptionBody
import com.quizapp.presentation.utils.UpdateQuizBottomSheetSubTitles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Todo: Add Validations

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateQuizViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val updateQuizState by viewModel.updateQuizState.collectAsState()
    val getQuestionsState by viewModel.getQuestionState.collectAsState()
    val getOptionsState by viewModel.getOptionsState.collectAsState()
    val updateQuestionState by viewModel.updateQuestionState.collectAsState()
    val updateOptionState by viewModel.updateOptionsState.collectAsState()
    val updateAnswerState by viewModel.updateAnswerState.collectAsState()

    val quizTitleDescriptionSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    val questionTitleDescriptionSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    BackHandler(quizTitleDescriptionSheetState.isVisible) {
        coroutineScope.launch {
            quizTitleDescriptionSheetState.hide()
        }
    }

    BackHandler(questionTitleDescriptionSheetState.isVisible) {
        coroutineScope.launch {
            questionTitleDescriptionSheetState.hide()
        }
    }

    UpdateQuizScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        quizTitleDescriptionSheetState = quizTitleDescriptionSheetState,
        questionTitleDescriptionSheetState = questionTitleDescriptionSheetState,
        coroutineScope = coroutineScope,
        updateQuizState = updateQuizState,
        getQuestionsState = getQuestionsState,
        getOptionsState = getOptionsState,
        updateQuestionState = updateQuestionState,
        updateOptionState = updateOptionState,
        updateAnswerState = updateAnswerState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UpdateQuizScreenContent(
    modifier: Modifier,
    viewModel: UpdateQuizViewModel,
    quizTitleDescriptionSheetState: ModalBottomSheetState,
    questionTitleDescriptionSheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    updateQuizState: UpdateQuizState,
    getQuestionsState: GetQuestionsState,
    getOptionsState: GetOptionsState,
    updateQuestionState: UpdateQuestionState,
    updateOptionState: UpdateOptionState,
    updateAnswerState: UpdateAnswerState
) {
    Scaffold(
        topBar = {
            CustomTopBarTitle(modifier = modifier.padding(top = 16.dp), title = "Update Quiz")
        }
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            if (viewModel.currentSection == UpdateSection.TITLE_DESCR) {
                UpdateQuizTitleDescrSection(
                    modifier = modifier,
                    viewModel = viewModel,
                    sheetState = quizTitleDescriptionSheetState,
                    coroutineScope = coroutineScope,
                    updateQuizState = updateQuizState
                )
            } else {
                UpdateQuestionSection(
                    modifier = modifier,
                    sheetState = questionTitleDescriptionSheetState,
                    coroutineScope = coroutineScope,
                    viewModel = viewModel,
                    getQuestionsState = getQuestionsState,
                    getOptionsState = getOptionsState,
                    updateQuestionState = updateQuestionState,
                    updateOptionState = updateOptionState,
                    updateAnswerState = updateAnswerState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UpdateQuizTitleDescrSection(
    modifier: Modifier,
    viewModel: UpdateQuizViewModel,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    updateQuizState: UpdateQuizState
) {
    ModalBottomSheetLayout(
        modifier = modifier.fillMaxSize(),
        sheetContent = {
            QuizTitleDescriptionSheetContent(
                modifier = modifier,
                viewModel = viewModel,
                sheetState = sheetState,
                coroutineScope = coroutineScope
            )
        },
        sheetState = sheetState,
        sheetBackgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (updateQuizState) {
                is UpdateQuizState.Nothing -> {
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 32.dp),
                        text = "You can update your Quiz Title or Quiz Description.",
                        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.Start
                    )
                    UpdateContent(
                        modifier = modifier,
                        iconId = R.drawable.ic_baseline_subtitles,
                        subTitle = UpdateQuizBottomSheetSubTitles.TITLE,
                        value = viewModel.quizTitle,
                        onClick = {
                            coroutineScope.launch {
                                viewModel.setIsQuizTitleSelected(true)
                                sheetState.show()
                            }
                        }
                    )
                    Divider(
                        modifier = modifier.fillMaxWidth(),
                        thickness = 1.dp,
                    )
                    UpdateContent(
                        modifier = modifier,
                        iconId = R.drawable.ic_baseline_description,
                        subTitle = UpdateQuizBottomSheetSubTitles.DESCR,
                        value = viewModel.quizDescr,
                        onClick = {
                            coroutineScope.launch {
                                viewModel.setIsQuizTitleSelected(false)
                                sheetState.show()
                            }
                        }
                    )
                    OutBtnCustom(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 32.dp),
                        onClick = {
                            viewModel.setSection(UpdateSection.QUESTIONS)
                            viewModel.getQuestions()
                        },
                        buttonText = "Next"
                    )
                }
                is UpdateQuizState.Loading -> {
                    CustomLoadingSpinner()
                }
                is UpdateQuizState.Success -> {
                    ShowMessage(message = "Successfully updated")
                    viewModel.resetUpdateQuizState()
                }
                is UpdateQuizState.Error -> {
                    ShowMessage(message = updateQuizState.errorMessage)
                    viewModel.resetUpdateQuizState()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuizTitleDescriptionSheetContent(
    modifier: Modifier,
    viewModel: UpdateQuizViewModel,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    if (viewModel.isQuizTitleSelected) {
        BottomSheet(
            modifier = modifier,
            value = viewModel.quizTitleBottomSheet,
            onValueChanged = { viewModel.updateQuizTitleBottomSheet(it) },
            subTitle = UpdateQuizBottomSheetSubTitles.TITLE,
            placeHolderText = UpdateQuizBottomSheetSubTitles.TITLE,
            onCancelClick = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            },
            onSaveClick = {
                viewModel.updateQuiz()
                coroutineScope.launch {
                    sheetState.hide()
                }
            },
        )
    } else {
        BottomSheet(
            modifier = modifier,
            value = viewModel.quizDescrBottomSheet,
            onValueChanged = { viewModel.updateQuizDescrBottomSheet(it) },
            subTitle = UpdateQuizBottomSheetSubTitles.DESCR,
            placeHolderText = UpdateQuizBottomSheetSubTitles.DESCR,
            onCancelClick = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            },
            onSaveClick = {
                viewModel.updateQuiz()
                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UpdateQuestionSection(
    modifier: Modifier,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    viewModel: UpdateQuizViewModel,
    getQuestionsState: GetQuestionsState,
    getOptionsState: GetOptionsState,
    updateQuestionState: UpdateQuestionState,
    updateOptionState: UpdateOptionState,
    updateAnswerState: UpdateAnswerState
) {
    ModalBottomSheetLayout(
        modifier = modifier.fillMaxSize(),
        sheetState = sheetState,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            QuestionSheetContent(
                modifier = modifier,
                viewModel = viewModel,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                updateQuestionState = updateQuestionState
            )
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (getQuestionsState) {
                is GetQuestionsState.Loading -> {
                    CustomLoadingSpinner()
                }
                is GetQuestionsState.Success -> {
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        text = "You can update your Question Title, Description or Answers.",
                        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.Start
                    )
                    QuestionTitleDescription(
                        modifier = modifier,
                        title = getQuestionsState.data.questionList[viewModel.questionIndex].title,
                        description = getQuestionsState.data.questionList[viewModel.questionIndex].description,
                        coroutineScope = coroutineScope,
                        viewModel = viewModel,
                        sheetState = sheetState
                    )
                    Options(
                        modifier = modifier,
                        getOptionsState = getOptionsState,
                        onOptionChecked = {
                            viewModel.setIsAnswerClicked(true)
                            viewModel.setNewAnswer(it)
                        },
                        updateOptionState = updateOptionState,
                        onResetUpdateOptionState = { viewModel.resetUpdateOptionState() },
                        onResetAnswerState = { viewModel.resetUpdateAnswerState() },
                        coroutineScope = coroutineScope,
                        sheetState = sheetState,
                        onSetSelectedOptionId = { id, descr ->
                            viewModel.setSelectedOption(id, descr)
                        },
                        onSetBottomSheet = {
                            viewModel.setQuestionBottomSheet(QuestionBottomSheet.QUESTION_OPTION)
                        },
                        newAnswerId = viewModel.newAnswerId,
                        onSetOldAnswer = {
                            viewModel.setOldAnswer(it)
                        },
                        updateAnswerState = updateAnswerState
                    )
                    QuestionButtons(
                        modifier = modifier,
                        viewModel = viewModel
                    )
                }
                is GetQuestionsState.Error -> {
                    ShowMessage(message = getQuestionsState.errorMessage)
                    viewModel.resetGetQuestionsState()
                    viewModel.setSection(UpdateSection.TITLE_DESCR)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuestionTitleDescription(
    modifier: Modifier,
    title: String,
    description: String,
    coroutineScope: CoroutineScope,
    viewModel: UpdateQuizViewModel,
    sheetState: ModalBottomSheetState
) {
    UpdateContent(
        modifier = modifier.fillMaxWidth(),
        iconId = R.drawable.ic_baseline_subtitles,
        subTitle = UpdateQuizBottomSheetSubTitles.QUEST_TITLE,
        value = title,
        onClick = {
            coroutineScope.launch {
                viewModel.setQuestionBottomSheet(QuestionBottomSheet.QUESTION_TITLE)
                sheetState.show()
            }
        }
    )
    Divider(
        modifier = modifier.fillMaxWidth(),
        thickness = 1.dp
    )
    UpdateContent(
        modifier = modifier.fillMaxWidth(),
        iconId = R.drawable.ic_baseline_description,
        subTitle = UpdateQuizBottomSheetSubTitles.QUEST_DESCR,
        value = description,
        onClick = {
            coroutineScope.launch {
                viewModel.setQuestionBottomSheet(QuestionBottomSheet.QUESTION_DESCR)
                sheetState.show()
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Options(
    modifier: Modifier,
    getOptionsState: GetOptionsState,
    onOptionChecked: (String) -> Unit,
    updateOptionState: UpdateOptionState,
    onResetUpdateOptionState: () -> Unit,
    coroutineScope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    updateAnswerState: UpdateAnswerState,
    onSetSelectedOptionId: (String, String) -> Unit,
    onSetBottomSheet: () -> Unit,
    onSetOldAnswer: (String) -> Unit,
    onResetAnswerState: () -> Unit,
    newAnswerId: String
) {
    when (getOptionsState) {
        is GetOptionsState.Loading -> {
            CustomLoadingSpinner()
        }
        is GetOptionsState.Success -> {
            when (updateOptionState) {
                is UpdateOptionState.Nothing -> {
                    when(updateAnswerState) {
                        is UpdateAnswerState.Nothing -> {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                            ) {
                                getOptionsState.data.options.forEach {

                                    if(it.isAnswer) {
                                        onSetOldAnswer(it.optionId)
                                    }

                                    Answer(
                                        modifier = modifier,
                                        answerText = it.description,
                                        onChecked = {
                                            onOptionChecked(it.optionId)
                                        },
                                        checked = it.isAnswer || newAnswerId == it.optionId,
                                        onAnswerClick = {
                                            onSetSelectedOptionId(it.optionId, it.description)
                                            onSetBottomSheet()
                                            coroutineScope.launch {
                                                sheetState.show()
                                            }
                                        },
                                        tint = if (!it.isAnswer && newAnswerId == it.optionId) {
                                            LightYellow
                                        } else {
                                            if (it.isAnswer) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant
                                        }
                                    )
                                }
                            }
                        }
                        is UpdateAnswerState.Loading -> {
                            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CustomLoadingSpinner()
                            }
                        }
                        is UpdateAnswerState.Success -> {
                            ShowMessage(message = "Your answer successfully updated !!!")
                            onResetAnswerState()
                        }
                        is UpdateAnswerState.Error -> {
                            ShowMessage(message = updateAnswerState.errorMessage)
                            onResetAnswerState()
                        }
                    }
                }
                is UpdateOptionState.Loading -> {
                    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CustomLoadingSpinner()
                    }
                }
                is UpdateOptionState.Success -> {
                    ShowMessage(message = "Your option successfully updated !!!")
                    onResetUpdateOptionState()
                }
                is UpdateOptionState.Error -> {
                    ShowMessage(message = updateOptionState.errorMessage)
                    onResetUpdateOptionState()
                }
            }
        }
        is GetOptionsState.Error -> {
            ShowMessage(message = getOptionsState.errorMessage)
        }
    }
}

@Composable
private fun Answer(
    modifier: Modifier,
    answerText: String,
    onChecked: () -> Unit,
    checked: Boolean,
    onAnswerClick: () -> Unit,
    tint: Color
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
            .clickable(enabled = true, onClick = onAnswerClick),
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
            selected = checked,
            tint = tint
        )
    }
}

@Composable
private fun QuestionButtons(
    modifier: Modifier,
    viewModel: UpdateQuizViewModel
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutBtnCustom(
            modifier = modifier.weight(1f),
            backgroundColor = if (viewModel.isAnswerClicked) FailRed else MaterialTheme.colors.onPrimary,
            onClick = {
                if (viewModel.isAnswerClicked) {
                    viewModel.setNewAnswer("")
                    viewModel.setIsAnswerClicked(false)
                } else {
                    viewModel.decQuestionIndex()
                    viewModel.setIsAnswerClicked(false)
                    viewModel.getOptions()

                    viewModel.setQuestValBottomSheet()
                }
            },
            buttonText = if (viewModel.isAnswerClicked) "Cancel" else "Previous",
            enabled = true
        )
        OutBtnCustom(
            modifier = modifier.weight(1f),
            backgroundColor = if (viewModel.isAnswerClicked) SuccessGreen else MaterialTheme.colors.onPrimary,
            onClick = {
                if (viewModel.isAnswerClicked) {
                    viewModel.updateAnswer()

                    viewModel.setIsAnswerClicked(false)
                } else {
                    viewModel.incQuestionIndex()
                    viewModel.setIsAnswerClicked(false)
                    viewModel.getOptions()

                    viewModel.setQuestValBottomSheet()
                }
            },
            buttonText = if (viewModel.isAnswerClicked) "Update" else "Next"
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuestionSheetContent(
    modifier: Modifier,
    viewModel: UpdateQuizViewModel,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    updateQuestionState: UpdateQuestionState
) {
    when (updateQuestionState) {
        is UpdateQuestionState.Nothing -> {
            when (viewModel.questBottomSheet) {
                QuestionBottomSheet.QUESTION_TITLE -> {
                    BottomSheet(
                        modifier = modifier,
                        value = viewModel.questTitleBottomSheet,
                        onValueChanged = { viewModel.updateQuestTitleBottomSheet(it) },
                        onCancelClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        onSaveClick = {
                            viewModel.updateQuestion()
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        subTitle = UpdateQuizBottomSheetSubTitles.QUEST_TITLE,
                        placeHolderText = UpdateQuizBottomSheetSubTitles.QUEST_TITLE
                    )
                }
                QuestionBottomSheet.QUESTION_DESCR -> {
                    BottomSheet(
                        modifier = modifier,
                        value = viewModel.questDescrBottomSheet,
                        onValueChanged = { viewModel.updateQuestDescrBottomSheet(it) },
                        onCancelClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        onSaveClick = {
                            viewModel.updateQuestion()
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        subTitle = UpdateQuizBottomSheetSubTitles.QUEST_DESCR,
                        placeHolderText = UpdateQuizBottomSheetSubTitles.QUEST_DESCR
                    )
                }
                else -> {
                    BottomSheet(
                        modifier = modifier,
                        value = viewModel.optionBottomSheet,
                        onValueChanged = { viewModel.updateOptionBottomSheet(it) },
                        onCancelClick = {
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        onSaveClick = {
                            viewModel.updateOption(
                                UpdateOptionBody(
                                    id = viewModel.selectedOptionId,
                                    description = viewModel.optionBottomSheet
                                )
                            )
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        subTitle = UpdateQuizBottomSheetSubTitles.OPTION,
                        placeHolderText = UpdateQuizBottomSheetSubTitles.OPTION
                    )
                }
            }
        }
        is UpdateQuestionState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CustomLoadingSpinner()
            }
        }
        is UpdateQuestionState.Success -> {
            ShowMessage(message = "Your question updated successfully !!!")
            viewModel.resetUpdateQuestionState()
            viewModel.getQuestions()
        }
        is UpdateQuestionState.Error -> {
            ShowMessage(message = updateQuestionState.errorMessage)
            viewModel.resetUpdateQuestionState()
        }
    }
}

@Composable
private fun UpdateContent(
    modifier: Modifier,
    iconId: Int,
    subTitle: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 32.dp)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant
            )
            Column(
                modifier = modifier.padding(start = 32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = subTitle,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colors.primaryVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
        }
        Icon(
            modifier = modifier.padding(end = 16.dp),
            painter = painterResource(id = R.drawable.ic_baseline_edit),
            contentDescription = null,
            tint = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun BottomSheet(
    modifier: Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    subTitle: String,
    placeHolderText: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = subTitle,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = { onValueChanged(it) },
            placeHolderText = placeHolderText,
            value = value
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancelClick) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
                )
            }
            TextButton(onClick = onSaveClick) {
                Text(
                    text = "Save",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
private fun ShowMessage(message: String) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
}