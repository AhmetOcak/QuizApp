package com.quizapp.presentation.leaderboard

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.quizapp.R
import com.quizapp.core.common.loadImage
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.OnBackPressed
import com.quizapp.core.ui.theme.*
import com.quizapp.domain.model.user.Leaderboard
import com.quizapp.presentation.utils.Dimens

@Composable
fun LeaderboardScreen(
    modifier: Modifier = Modifier,
    viewModel: LeaderboardViewModel = hiltViewModel()
) {

    val leaderboardState by viewModel.leaderboardState.collectAsState()

    val activity = LocalContext.current as Activity
    OnBackPressed(activity = activity)

    LeaderboardScreenContent(modifier = modifier, leaderboardState = leaderboardState)
}

@Composable
private fun LeaderboardScreenContent(modifier: Modifier, leaderboardState: LeaderboardState) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (leaderboardState) {
            is LeaderboardState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CustomLoadingSpinner()
                }
            }
            is LeaderboardState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Title()
                    TopThreeSection(
                        modifier = modifier,
                        first = leaderboardState.data[0],
                        second = leaderboardState.data[1],
                        third = leaderboardState.data[2]
                    )
                }
                QueueSection(
                    modifier = modifier.weight(1f),
                    leaderboard = leaderboardState.data.slice(3 until leaderboardState.data.size)
                )
            }
            is LeaderboardState.Error -> {
                LeaderboardError(modifier = modifier, leaderboardState.errorMessage)
            }
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = "Leaderboard",
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun LeaderboardError(modifier: Modifier, errorMessage: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = modifier.size(48.dp),
                painter = painterResource(id = R.drawable.error_image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = modifier.padding(top = 16.dp),
                text = errorMessage,
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
private fun TopThreeSection(
    modifier: Modifier,
    first: Leaderboard,
    second: Leaderboard,
    third: Leaderboard
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Second(
            modifier = modifier,
            userImage = second.userImage,
            userName = second.userName,
            userScore = second.score
        )
        First(
            modifier = modifier,
            userImage = first.userImage,
            userName = first.userName,
            userScore = first.score
        )
        Third(
            modifier = modifier,
            userImage = third.userImage,
            userName = third.userName,
            userScore = third.score
        )
    }
}

// Created for TopThree
@Composable
private fun First(modifier: Modifier, userImage: String, userScore: Int, userName: String) {
    Column(
        modifier = modifier
            .padding(bottom = 32.dp)
            .zIndex(2f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(bottom = 8.dp),
            text = "1",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
        )
        Image(
            modifier = modifier
                .size(48.dp)
                .padding(bottom = 8.dp),
            painter = painterResource(id = R.drawable.crowns),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        ProfileImage(
            modifier = modifier,
            userImage = userImage,
            imageSize = 136.dp
        )
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = userName,
            style = MaterialTheme.typography.h3
        )
        Text(
            text = "$userScore",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
        )
    }
}

// Created for TopThree
@Composable
private fun Second(modifier: Modifier, userImage: String, userName: String, userScore: Int) {
    Column(
        modifier = modifier
            .offset(x = 32.dp)
            .zIndex(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(bottom = 8.dp),
            text = "2",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
        ProfileImage(modifier = modifier, userImage = userImage)
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = userName,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )
        Text(
            text = "$userScore",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

// Created for TopThree
@Composable
private fun Third(modifier: Modifier, userImage: String, userName: String, userScore: Int) {
    Column(
        modifier = modifier
            .offset(x = (-32).dp)
            .zIndex(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(bottom = 8.dp),
            text = "3",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
        ProfileImage(modifier = modifier, userImage = userImage)
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = userName,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )
        Text(
            text = "$userScore",
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
private fun ProfileImage(modifier: Modifier, userImage: String, imageSize: Dp = 112.dp) {
    Card(
        modifier = modifier
            .size(imageSize)
            .shadow(
                ambientColor = StrangeRed,
                spotColor = StrangeRed,
                elevation = 32.dp,
                shape = CircleShape
            ),
        shape = CircleShape,
        border = BorderStroke(
            width = 4.dp,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    LightPurple,
                    LightPink,
                    StrangeRed,
                    StrangePurple,
                    LightBrown,
                    LightYellow
                )
            )
        ),
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape),
            model = loadImage(context = LocalContext.current, imageUrl = userImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun QueueSection(modifier: Modifier, leaderboard: List<Leaderboard>) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 32.dp, bottom = 32.dp + Dimens.AppBarDefaultHeight),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(leaderboard) {
            Queue(
                modifier = Modifier,
                userImage = it.userImage,
                userName = it.userName,
                userScore = it.score,
                position = (leaderboard.indexOf(it) + 3) + 1
            )
        }
    }
}

@Composable
private fun Queue(
    modifier: Modifier,
    userImage: String,
    userName: String,
    userScore: Int,
    position: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$position",
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
        Row(
            modifier = modifier
                .padding(start = 32.dp)
                .height(80.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(50)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QueueContent(
                modifier = modifier,
                userImage = userImage,
                userName = userName,
                userScore = userScore
            )
        }
    }
}

// Created for Queue
@Composable
private fun QueueContent(modifier: Modifier, userImage: String, userName: String, userScore: Int) {
    Row(
        modifier = modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = modifier
                .size(80.dp)
                .clip(shape = CircleShape),
            model = loadImage(context = LocalContext.current, imageUrl = userImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = modifier.padding(start = 16.dp),
            text = userName,
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )
    }
    Text(
        modifier = modifier.padding(end = 32.dp),
        text = "$userScore",
        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}