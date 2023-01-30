package com.quizapp.presentation.leaderboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.quizapp.R
import com.quizapp.core.ui.theme.*
import com.quizapp.presentation.utils.Dimens

/*
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
WARNING WARNING WARNING WARNING WARNING
BU SAYFADA RESPONSIVE DESIGN İÇİN SIZE AYARLAMALARI YAPILACAKTIR
WARNING WARNING WARNING WARNING WARNING
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

@Composable
fun LeaderboardScreen(modifier: Modifier = Modifier) {

    LeaderboardScreenContent(modifier = modifier)
}

@Composable
private fun LeaderboardScreenContent(modifier: Modifier) {
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
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Title()
            TopThreeSection(modifier = modifier)
        }
        QueueSection(modifier = modifier.weight(1f))
    }
}

@Composable
private fun Title() {
    Text(
        text = "Leaderboard",
        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun TopThreeSection(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Second(
            modifier = modifier,
            userImage = R.drawable.me,
            userName = "Micheal",
            userScore = 1021
        )
        First(
            modifier = modifier,
            userImage = R.drawable.me,
            userName = "Lale",
            userScore = 1453
        )
        Third(
            modifier = modifier,
            userImage = R.drawable.me,
            userName = "Jackson",
            userScore = 1000
        )
    }
}

// Created for TopThree
@Composable
private fun First(modifier: Modifier, userImage: Int, userScore: Int, userName: String) {
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
private fun Second(modifier: Modifier, userImage: Int, userName: String, userScore: Int) {
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
private fun Third(modifier: Modifier, userImage: Int, userName: String, userScore: Int) {
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
private fun ProfileImage(modifier: Modifier, userImage: Int, imageSize: Dp = 112.dp) {
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
        Image(
            modifier = modifier
                .fillMaxSize()
                .clip(CircleShape),
            painter = painterResource(id = userImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun QueueSection(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 32.dp, bottom = 32.dp + Dimens.AppBarDefaultHeight),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            Queue(
                modifier = Modifier,
                userImage = R.drawable.me,
                userName = "Ahmet",
                userScore = 999,
                position = 99
            )
        }
    }
}

@Composable
private fun Queue(
    modifier: Modifier,
    userImage: Int,
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
private fun QueueContent(modifier: Modifier, userImage: Int, userName: String, userScore: Int) {
    Row(
        modifier = modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .size(80.dp)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(id = userImage),
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