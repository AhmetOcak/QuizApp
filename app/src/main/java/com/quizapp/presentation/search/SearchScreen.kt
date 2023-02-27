package com.quizapp.presentation.search

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.quizapp.R
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.OnBackPressed
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.theme.*
import com.quizapp.data.datasource.remote.quiz.entity.RecordsDto
import com.quizapp.presentation.utils.Dimens

@Composable
fun SearchScreen(modifier: Modifier = Modifier, viewModel: SearchViewModel = hiltViewModel()) {

    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    SearchScreenContent(
        modifier = modifier,
        searchFieldValue = viewModel.searchKeyword,
        searchOnValChanged = { viewModel.updateSearchField(it) },
        viewModel = viewModel
    )
}

@Composable
private fun SearchScreenContent(
    modifier: Modifier,
    searchFieldValue: String,
    searchOnValChanged: (String) -> Unit,
    viewModel: SearchViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchFieldSection(
            modifier = modifier,
            searchFieldValue = searchFieldValue,
            searchOnValChanged = searchOnValChanged
        )
        SearchResultSection(modifier = modifier, viewModel = viewModel)
    }
}

@Composable
private fun SearchFieldSection(
    modifier: Modifier,
    searchFieldValue: String,
    searchOnValChanged: (String) -> Unit
) {
    OtfCustom(
        modifier = modifier.fillMaxWidth(),
        onValueChanged = searchOnValChanged,
        placeHolderText = "Search",
        value = searchFieldValue
    )
}

@Composable
private fun SearchResultSection(modifier: Modifier, viewModel: SearchViewModel) {
    QuizList(modifier = modifier, viewModel = viewModel)
}

// Created for SearchResultSection
@Composable
private fun QuizList(modifier: Modifier, viewModel: SearchViewModel) {
    if (!viewModel.isSearchKeywordBlank()) {
        val quizzes = viewModel.getSearchResults().collectAsLazyPagingItems()

        LazyColumn(
            contentPadding = PaddingValues(
                top = 32.dp,
                bottom = 32.dp + Dimens.AppBarDefaultHeight
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = quizzes,
                key = { it.quizId }
            ) { quiz ->
                if (quiz != null) {
                    Quiz(
                        modifier = modifier,
                        quizName = quiz.title,
                        quizDescription = quiz.description
                    )
                }
            }

            loadStateRefresh(modifier = modifier, quizzes = quizzes, viewModel = viewModel)
            loadStateAppend(modifier = modifier, quizzes = quizzes, viewModel = viewModel)
        }
    } else {
        EmptySearch(modifier = modifier, message = "Search something")
    }
}

@Composable
private fun EmptySearch(modifier: Modifier, message: String) {
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
            text = message,
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ErrorSearch(modifier: Modifier, message: String) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier.size(96.dp),
            painter = painterResource(id = R.drawable.error_image),
            contentDescription = null
        )
        Text(
            modifier = modifier.padding(top = 8.dp),
            text = message,
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.primaryVariant,
            textAlign = TextAlign.Center
        )
    }
}

private fun LazyListScope.loadStateRefresh(
    modifier: Modifier,
    quizzes: LazyPagingItems<RecordsDto>,
    viewModel: SearchViewModel
) {
    when (quizzes.loadState.refresh) {
        is LoadState.Error -> {
            item {
                ErrorSearch(
                    modifier = modifier,
                    message = viewModel.setErrorMessage((quizzes.loadState.refresh as LoadState.Error).error)
                )
            }
        }
        is LoadState.Loading -> {
            item {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CustomLoadingSpinner()
                }
            }
        }
        else -> {
            if (quizzes.itemSnapshotList.isEmpty() && quizzes.loadState.refresh != LoadState.Loading && quizzes.loadState.append != LoadState.Loading) {
                item { EmptySearch(modifier = modifier, message = "No Result Found :(") }
            }
        }
    }
}

private fun LazyListScope.loadStateAppend(
    modifier: Modifier,
    quizzes: LazyPagingItems<RecordsDto>,
    viewModel: SearchViewModel
) {
    when (quizzes.loadState.append) {
        is LoadState.Error -> {
            item {
                ErrorSearch(
                    modifier = modifier,
                    message = viewModel.setErrorMessage((quizzes.loadState.append as LoadState.Error).error)
                )
            }
        }
        is LoadState.Loading -> {
            item {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomLoadingSpinner()
                }
            }
        }
        else -> {}
    }
}

@Composable
private fun Quiz(
    modifier: Modifier,
    quizName: String,
    quizDescription: String,
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
        )
    }
}

// Created for CreateQuizDto
@Composable
private fun QuizContent(
    modifier: Modifier,
    quizName: String,
    quizDescription: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        /*Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AuthorImage(modifier = modifier, authorImage = quizAuthorImage)
            Text(
                modifier = modifier.padding(start = 8.dp),
                text = quizAuthor,
                style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant
            )
        }*/
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

// Created for CreateQuizDto
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