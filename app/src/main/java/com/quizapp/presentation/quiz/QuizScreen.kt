package com.quizapp.presentation.quiz

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quizapp.R
import com.quizapp.core.ui.component.CircleCheckbox
import com.quizapp.core.ui.component.CustomSlider
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.core.ui.theme.*

private val gradientColors = listOf(
    LightPurple,
    LightPink,
    StrangeRed,
    StrangeOrange,
    LightBrown,
    LightYellow
)

@Composable
fun QuizScreen(modifier: Modifier = Modifier) {

    QuizScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun QuizScreenContent(modifier: Modifier) {
    Scaffold() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            TimerSection(modifier = modifier)
            QuestionSection(modifier = modifier)
            AnswersSection(modifier = modifier)
            OutBtnCustom(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                onClick = { /*TODO*/ },
                buttonText = "Next"
            )
        }
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
private fun QuestionSection(modifier: Modifier) {
    Column {
        QuestionTracker(modifier = modifier)
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 2.dp,
            color = Color.Gray
        )
        Question(modifier = modifier)
    }
}

@Composable
private fun QuestionTracker(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        text = "Question 1/10",
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.SemiBold),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun Question(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "What attraction in Montreal is one of the largest in the world",
        style = MaterialTheme.typography.h2.copy(color = Color.Black, fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun AnswersSection(modifier: Modifier) {
    Column(
        modifier = modifier.padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Answer(
            modifier = modifier.padding(vertical = 8.dp),
            answerText = "The Botanical Gardens",
            onChecked = {}
        )
        Answer(
            modifier = modifier.padding(vertical = 8.dp),
            answerText = "The Botanical Gardens",
            onChecked = {},
            checked = true
        )
        Answer(
            modifier = modifier.padding(vertical = 8.dp),
            answerText = "The Science Museum",
            onChecked = {}
        )
        Answer(
            modifier = modifier.padding(vertical = 8.dp),
            answerText = "The Olympic Stadium",
            onChecked = {}
        )
    }
}

// Answer Component
// Created for Answers Section
@Composable
private fun Answer(
    modifier: Modifier,
    answerText: String,
    onChecked: () -> Unit,
    checked: Boolean = false
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