package com.quizapp.presentation.quiz_result

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quizapp.R
import com.quizapp.core.ui.component.CustomVerticalDivider
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.core.ui.theme.*
import com.quizapp.domain.model.quiz.QuizResult
import com.quizapp.presentation.utils.Dimens

@Composable
fun QuizResultScreen(
    modifier: Modifier = Modifier,
    quizResult: QuizResult?
) {

    QuizResultScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun QuizResultScreenContent(modifier: Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TitleSection(modifier = modifier) }
    ) {
        QuizResultSection(
            modifier = modifier,
            onClose = {},
            userName = "Ahmet",
            score = 80,
            resultMessage = "Congrats",
            resultScoreMessage = "You are the best"
        )
    }
}

@Composable
private fun QuizResultSection(
    modifier: Modifier,
    score: Int,
    resultMessage: String,
    resultScoreMessage: String,
    userName: String,
    onClose: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ScoreCard(
            modifier = modifier.weight(1f),
            score = score,
            resultMessage = resultMessage,
            resultScoreMessage = resultScoreMessage
        )
        ResultDetail(
            modifier = modifier.weight(1f),
            userName = userName,
            onClose = onClose,
            hour = "01",
            minute = "22",
            seconds = "35"
        )
    }
}

// Created for QuizResultSection
@Composable
private fun ScoreCard(
    modifier: Modifier,
    score: Int,
    resultMessage: String,
    resultScoreMessage: String
) {
    Card(modifier = modifier, shape = RoundedCornerShape(10), backgroundColor = SuccessGreen ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResultMessage(message = resultMessage)
            ResultMessage(message = resultScoreMessage)
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = score.toString(),
                style = MaterialTheme.typography.h1.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 48.sp
                )
            )
        }
    }
}

@Composable
private fun ResultMessage(message: String) {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = message,
        style = MaterialTheme.typography.h1.copy(
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 32.sp
        )
    )
}

@Composable
private fun ResultDetail(
    modifier: Modifier,
    userName: String,
    onClose: () -> Unit,
    hour: String,
    minute: String,
    seconds: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        UserName(userName = userName)
        TimeSpentOnTheQuiz(hour = hour, minute = minute, seconds = seconds)
        OutBtnCustom(modifier = Modifier.width(128.dp), onClick = onClose, buttonText = "Close")
    }
}


@Composable
private fun UserName(userName: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 48.dp)
            .fillMaxWidth()
            .height(Dimens.ConstantHeight)
            .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(25))
            .clip(RoundedCornerShape(25))
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            LightPink,
                            StrangeRed,
                            StrangeOrange
                        )
                    )
                ),
                shape = RoundedCornerShape(25)
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = "Username: ",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )
        Text(
            text = userName,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun TimeSpentOnTheQuiz(hour: String, minute: String, seconds: String) {
    Row(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth()
            .height(Dimens.ConstantHeight)
            .background(color = Color.Blue, shape = RoundedCornerShape(25))
            .clip(RoundedCornerShape(25)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(id = R.drawable.ic_baseline_access_time),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Quiz Time",
            style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.primaryVariant)
        )
        QuizTime(hour = hour, minute = minute, seconds = seconds)
    }
}

@Composable
private fun QuizTime(hour: String, minute: String, seconds: String) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 16.dp)
            .background(color = MaterialTheme.colors.surface, shape = RoundedCornerShape(25))
            .clip(RoundedCornerShape(25)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Time(time = hour, timeType = "Hour")
        CustomVerticalDivider()
        Time(time = minute, timeType = "Minute")
        CustomVerticalDivider()
        Time(time = seconds, timeType = "Seconds")
    }
}

@Composable
private fun Time(time: String, timeType: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
        Text(
            modifier = Modifier,
            text = timeType,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        )
    }
}

@Composable
private fun TitleSection(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = "Quiz Result",
        style = MaterialTheme.typography.h1.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.primaryVariant
        ),
        textAlign = TextAlign.Center
    )
}