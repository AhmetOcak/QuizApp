package com.quizapp.presentation.quiz_landing

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quizapp.R
import com.quizapp.core.ui.component.OutBtnDefault
import com.quizapp.core.ui.theme.WhiteSmoke

const val description =
    "Prussia was a German state on the southeast coast of the Baltic Sea. It formed the German Empire under Prussian rule when it united the German states in 1871. It was de facto dissolved by an emergency decree transferring powers of the Prussian government to German Chancellor Franz von Papen in 1932 and de jure by an Allied decree in 1947."

@Composable
fun QuizLandingScreen(modifier: Modifier = Modifier) {
    QuizLandingScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun QuizLandingScreenContent(modifier: Modifier) {
    Scaffold(
        backgroundColor = WhiteSmoke,
        topBar = {
            MyTopAppBar()
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LandingImage(modifier = modifier)
            QuizName(modifier = modifier, quizName = "Prussia History")
            QuizDescription(modifier = modifier, quizDescription = description)
            StartQuiz(modifier = modifier)
        }
    }
}

@Composable
private fun LandingImage(modifier: Modifier) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp),
        painter = painterResource(id = R.drawable.quiz_landing),
        contentDescription = null
    )
}

@Composable
private fun QuizName(modifier: Modifier, quizName: String) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 64.dp, bottom = 32.dp),
        text = quizName,
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun QuizDescription(modifier: Modifier, quizDescription: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = quizDescription,
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun StartQuiz(modifier: Modifier) {
    OutBtnDefault(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = { /*TODO*/ },
        buttonText = "Start"
    )
}

@Composable
private fun MyTopAppBar() {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_cancel),
                    contentDescription = null
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    )
}