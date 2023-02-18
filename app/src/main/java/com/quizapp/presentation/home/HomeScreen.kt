package com.quizapp.presentation.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.ui.component.OnBackPressed
import com.quizapp.core.ui.theme.*
import com.quizapp.presentation.signin.SignInViewModel
import com.quizapp.presentation.utils.Dimens
import retrofit2.http.GET

@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = hiltViewModel())  {

    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    HomeScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun HomeScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarSection(modifier = modifier)
        PopularQuizzesSection(modifier = modifier)
        QuizCategoriesSection(modifier = modifier)
    }
}

@Composable
private fun TopBarSection(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(horizontal = 16.dp)
    ) {
        ProfileImage(modifier = modifier)
        UserNameLevel(modifier = modifier)
        Notification(modifier = modifier)
    }
}

// Created for Topbar Section
@Composable
private fun ProfileImage(modifier: Modifier) {
    Image(
        modifier = modifier
            .size(72.dp)
            .clip(shape = CircleShape)
            .border(
                border = BorderStroke(
                    width = 2.dp, brush = Brush.horizontalGradient(
                        colors = listOf(Sunset, Grapefruit)
                    )
                ),
                shape = CircleShape
            ),
        painter = painterResource(id = R.drawable.me),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

// Created for Topbar Section
@Composable
private fun UserNameLevel(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello, Ahmet",
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        Text(
            text = "Lv. 1  Beginner",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

// Created for Topbar Section
@Composable
private fun Notification(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
        IconButton(
            modifier = modifier
                .clip(shape = RoundedCornerShape(20))
                .background(color = MaterialTheme.colors.primary),
            onClick = { /*TODO*/ }) {
            Icon(
                modifier = modifier.size(24.dp),
                painter = painterResource(id = R.drawable.notification_alert),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun PopularQuizzesSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        Text(
            modifier = modifier.padding(start = 16.dp),
            text = "Popular Quizzes",
            style = MaterialTheme.typography.h2.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                PopularQuizCard(
                    modifier = modifier,
                    onClick = { /*TODO*/ },
                    quizName = "Math"
                )
            }
        }
    }
}

@Composable
private fun QuizCategoriesSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.h2.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        // 56.dp added (offset image value)
        LazyColumn(
            contentPadding = PaddingValues(
                top = 72.dp,
                bottom = 72.dp + Dimens.AppBarDefaultHeight
            ),
            verticalArrangement = Arrangement.spacedBy(64.dp),
        ) {
            items(5) {
                CategoryCard(modifier = modifier, onClick = {})
            }
        }
    }
}

@Composable
private fun PopularQuizCard(
    modifier: Modifier,
    onClick: () -> Unit,
    quizName: String
) {
    Card(
        modifier = modifier
            .width(144.dp)
            .height(176.dp),
        shape = RoundedCornerShape(20)
    ) {
        QuizCardBackground(modifier = modifier, isQuizCard = true)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            QuizCardText(modifier = modifier, quizName = quizName)
            QuizCardButton(modifier = modifier, onClick = onClick)
        }
    }
}

// Created for Quiz Card and Category Card
@Composable
private fun QuizCardBackground(modifier: Modifier, isQuizCard: Boolean) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = modifier.fillMaxSize(),
            painter = painterResource(id = if (isQuizCard) R.drawable.quiz_back else R.drawable.categori_back),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

// Created for Quiz Card
@Composable
private fun QuizCardText(modifier: Modifier, quizName: String) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = quizName,
            style = MaterialTheme.typography.h3.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        Text(
            text = "Quiz",
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
    }
}

// Created for Quiz Card
@Composable
private fun QuizCardButton(modifier: Modifier, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(),
        shape = RoundedCornerShape(30),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(
            width = 1.dp,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    LightPurple,
                    LightPink,
                    StrangeRed,
                    StrangeOrange
                )
            )
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_play_arrow),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
private fun CategoryCard(modifier: Modifier, onClick: () -> Unit) {
    Box {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(25)
        ) {
            QuizCardBackground(modifier = modifier, isQuizCard = false)
            CategoryInterface(modifier = modifier, categoryName = "Literature", onClick = onClick)
        }
        CategoryImage(modifier = modifier, image = R.drawable.books)
    }
}

// Created for Category Card
@Composable
private fun CategoryImage(modifier: Modifier, image: Int) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 32.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            modifier = modifier
                .size(112.dp)
                .offset(y = (-56).dp),
            painter = painterResource(id = R.drawable.books),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

// Created for Category Card
@Composable
private fun CategoryInterface(modifier: Modifier, categoryName: String, onClick: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        CategoryCardButton(
            modifier = modifier,
            onClick = onClick
        )
        Text(
            modifier = modifier.padding(start = 32.dp, bottom = 24.dp),
            text = categoryName,
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
    }
}

// Created for Category Card
@Composable
private fun CategoryCardButton(modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier
            .padding(start = 32.dp, top = 24.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = Color.White),
                shape = RoundedCornerShape(25)
            ),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_play_arrow),
            contentDescription = null,
            tint = Color.White
        )
    }
}