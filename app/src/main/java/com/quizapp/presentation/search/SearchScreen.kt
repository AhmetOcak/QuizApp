package com.quizapp.presentation.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quizapp.R
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.theme.*
import com.quizapp.presentation.utils.Dimens

const val description =
    "Prussia was a German state on the southeast coast of the Baltic Sea. It formed the German Empire under Prussian rule when it united the German states in 1871"

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {

    SearchScreenContent(modifier = modifier)
}

@Composable
private fun SearchScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchFieldSection(modifier = modifier)
        SearchResultSection(modifier = modifier)
    }
}

@Composable
private fun SearchFieldSection(modifier: Modifier) {
    OtfCustom(
        modifier = modifier.fillMaxWidth(),
        onValueChanged = {},
        placeHolderText = "Search",
    )
}

@Composable
private fun SearchResultSection(modifier: Modifier) {
    EmptySearch(modifier = modifier)
    //QuizList(modifier = modifier)
}


// Created for SearchResultSection
@Composable
private fun EmptySearch(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.size(96.dp),
            painter = painterResource(id = R.drawable.empty_box),
            contentDescription = null
        )
        Text(
            modifier = modifier.padding(top = 8.dp),
            text = "Sorry! No result found :(",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

// Created for SearchResultSection
@Composable
private fun QuizList(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 32.dp, bottom = 32.dp + Dimens.AppBarDefaultHeight),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            Quiz(
                modifier = modifier,
                quizName = "Prussia History",
                quizDescription = description,
                quizAuthor = "Ahmet Ocak",
                quizAuthorImage = R.drawable.me
            )
        }
    }
}

@Composable
private fun Quiz(
    modifier: Modifier,
    quizName: String,
    quizDescription: String,
    quizAuthor: String,
    quizAuthorImage: Int
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(208.dp)
            .clickable(onClick = {}),
        shape = RoundedCornerShape(25)
    ) {
        QuizCardBackground(modifier = modifier)
        QuizContent(
            modifier = modifier,
            quizName = quizName,
            quizDescription = quizDescription,
            quizAuthor = quizAuthor,
            quizAuthorImage = quizAuthorImage
        )
    }
}

// Created for Quiz
@Composable
private fun QuizContent(
    modifier: Modifier,
    quizName: String,
    quizDescription: String,
    quizAuthor: String,
    quizAuthorImage: Int
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AuthorImage(modifier = modifier, authorImage = quizAuthorImage)
            Text(
                modifier = modifier.padding(start = 8.dp),
                text = quizAuthor,
                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = quizName,
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primaryVariant
        )
        Text(
            modifier = modifier
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState()),
            text = quizDescription,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

// Created for QuizContent
@Composable
private fun AuthorImage(modifier: Modifier, authorImage: Int) {
    Image(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .border(
                border = BorderStroke(width = 1.dp, color = StrangeOrange),
                shape = CircleShape
            ),
        painter = painterResource(id = authorImage),
        contentDescription = null
    )
}

// Created for Quiz
@Composable
private fun QuizCardBackground(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.quiz_back),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}