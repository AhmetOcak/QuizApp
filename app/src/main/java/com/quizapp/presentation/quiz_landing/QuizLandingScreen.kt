package com.quizapp.presentation.quiz_landing

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.imageLoader
import com.quizapp.R
import com.quizapp.core.common.loadImage
import com.quizapp.core.ui.component.OutBtnCustom

@Composable
fun QuizLandingScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizLandingViewModel = hiltViewModel()
) {
    QuizLandingScreenContent(
        modifier = modifier,
        quizTitle = viewModel.quizTitle,
        quizDescription = viewModel.quizDescription,
        quizAuthorUserName = viewModel.quizAuthorUserName,
        quizCreatedDate = viewModel.quizCreatedDate,
        startQuiz = { viewModel.navigateQuizScreen() },
        categoryName = viewModel.categoryName,
        authorUserImage = viewModel.quizAuthorUserImage,
        navigateBack = { viewModel.navigateBackScreen() }
    )

    BackHandler {
        viewModel.navigateBackScreen()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun QuizLandingScreenContent(
    modifier: Modifier,
    quizTitle: String,
    quizDescription: String,
    quizAuthorUserName: String,
    quizCreatedDate: String,
    startQuiz: () -> Unit,
    categoryName: String,
    authorUserImage: String,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            MyTopAppBar(navigateBack = navigateBack)
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
            QuizName(
                modifier = modifier,
                quizName = quizTitle
            )
            QuizAuthorNameAndCreatedDate(
                modifier = modifier,
                authorName = quizAuthorUserName,
                createdDate = quizCreatedDate,
                authorUserImage = authorUserImage
            )
            QuizCategoryName(
                modifier = modifier,
                categoryName = categoryName
            )
            QuizDescription(
                modifier = modifier,
                quizDescription = quizDescription
            )
            StartQuiz(
                modifier = modifier,
                startQuiz = startQuiz
            )
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
            .padding(top = 64.dp),
        text = quizName,
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun QuizAuthorNameAndCreatedDate(
    modifier: Modifier,
    authorName: String,
    createdDate: String,
    authorUserImage: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = loadImage(context = LocalContext.current, imageUrl = authorUserImage),
            imageLoader = LocalContext.current.imageLoader,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = modifier.padding(start = 8.dp),
            text = authorName,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Normal),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.primaryVariant
        )
        Text(
            modifier = modifier.padding(horizontal = 16.dp),
            text = "-",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.primaryVariant
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_calendar_month),
            contentDescription = null,
            tint = MaterialTheme.colors.primaryVariant
        )
        Text(
            modifier = modifier.padding(start = 8.dp),
            text = createdDate,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Normal),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun QuizCategoryName(
    modifier: Modifier,
    categoryName: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_collections_bookmark),
            contentDescription = null,
            tint = MaterialTheme.colors.primaryVariant
        )
        Text(
            modifier = modifier.padding(start = 8.dp),
            text = categoryName,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Normal),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun QuizDescription(modifier: Modifier, quizDescription: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = quizDescription,
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun StartQuiz(modifier: Modifier, startQuiz: () -> Unit) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = startQuiz,
        buttonText = "Start"
    )
}

@Composable
private fun MyTopAppBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_cancel),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        },
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    )
}