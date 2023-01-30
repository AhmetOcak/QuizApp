package com.quizapp.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quizapp.R
import com.quizapp.core.ui.component.CustomSlider
import com.quizapp.core.ui.theme.*
import com.quizapp.presentation.utils.Dimens

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    ProfileScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ProfileScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 32.dp + Dimens.AppBarDefaultHeight
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileDetailSection(modifier = modifier)
        AchievementsSection(modifier = modifier)
        InventorySection(modifier = modifier)
    }
}

@Composable
private fun ProfileDetailSection(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileImage(modifier = modifier)
        ProfileInfo(modifier = modifier)
    }
}

// Created for Profile Detail Section
@Composable
private fun ProfileImage(modifier: Modifier) {
    Image(
        modifier = modifier
            .size(144.dp)
            .clip(shape = RoundedCornerShape(20)),
        painter = painterResource(id = R.drawable.me),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

// Created for Profile Detail Section
@Composable
private fun ProfileInfo(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserNameAndLevel(modifier = modifier)
        UserLevelAndStatistics(modifier = modifier)
    }
}

// Created for Profile Info
@Composable
private fun UserNameAndLevel(modifier: Modifier) {
    Text(
        text = "Ahmet Ocak",
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant
    )
    Text(
        text = "Bonus Booster, 24 lv",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.primaryVariant
    )
}

// Created for Profile Info
@Composable
private fun UserLevelAndStatistics(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(shape = RoundedCornerShape(20))
    ) {
        Column {
            LevelSlider(modifier = modifier)
            UserStatistics(modifier = modifier)
        }
    }
}

// Created for UserLevelAndStatistics
@Composable
private fun LevelSlider(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "4000 / 10000 XP",
            style = MaterialTheme.typography.h6.copy(color = Color.Gray),
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.primaryVariant
        )
        CustomSlider(
            modifier = modifier.fillMaxWidth(),
            trackHeight = 12.dp,
            onValueChange = { value -> },
            value = 40f
        )
    }
}

// Created for UserLevelAndStatistics
@Composable
private fun UserStatistics(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Statistics(
            modifier = modifier.padding(start = 4.dp),
            iconId = R.drawable.ic_baseline_arrow_circle_up,
            value = 910,
            description = "Highest Score"
        )
        Statistics(
            modifier = modifier.padding(start = 4.dp),
            iconId = R.drawable.ic_baseline_check_circle,
            value = 218,
            description = "Correct Answers"
        )
    }
}

// Statistics Component
// Created for Profile Info
@Composable
private fun Statistics(modifier: Modifier, iconId: Int, value: Int, description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedButton(
            modifier = modifier.size(48.dp),
            onClick = {},
            enabled = false,
            elevation = ButtonDefaults.elevation(disabledElevation = 8.dp),
            border = BorderStroke(
                width = 0.dp,
                color = Color.Transparent
            ),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(25),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = Color.Black
            )
        }
        Column(
            modifier = modifier.padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$value",
                style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primaryVariant
            )
            Text(
                text = description, style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
private fun AchievementsSection(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .clip(shape = RoundedCornerShape(20))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            TitleAndSlider(
                modifier = modifier,
                title = "Achievements",
                count = 8,
                sliderDescr = "8/200",
                value = 12f
            )
            UserItems(modifier = modifier)
        }
    }
}

@Composable
private fun InventorySection(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .clip(shape = RoundedCornerShape(20))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            TitleAndSlider(
                modifier = modifier,
                title = "Inventory",
                count = 4,
                sliderDescr = "4/10",
                value = 40f
            )
            UserItems(modifier = modifier)
        }
    }
}

// Created for AchievementsSection and InventorySection
@Composable
private fun TitleAndSlider(
    modifier: Modifier,
    title: String,
    count: Int,
    sliderDescr: String,
    value: Float
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleAndCount(modifier = modifier, title = title, count = count)
        Slider(modifier = modifier, sliderDescr = sliderDescr, value = value)
    }
}

// Created for AchievementsSection and InventorySection
@Composable
private fun TitleAndCount(modifier: Modifier, title: String, count: Int) {
    Row {
        Text(
            modifier = modifier.padding(end = 8.dp),
            text = title,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
        Box(
            modifier = modifier
                .size(24.dp)
                .clip(shape = RoundedCornerShape(20))
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(20)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

// Created for AchievementsSection and InventorySection
@Composable
private fun Slider(modifier: Modifier, sliderDescr: String, value: Float) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = sliderDescr, style = MaterialTheme.typography.h6.copy(Color.Gray))
        CustomSlider(
            modifier = modifier
                .width(96.dp)
                .padding(start = 8.dp),
            trackHeight = 8.dp,
            onValueChange = { value -> },
            value = value
        )
    }
}

// Created for AchievementsSection and InventorySection
@Composable
private fun UserItems(modifier: Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        items(5) {
            UserCard(
                modifier = modifier,
                contentName = "Leader",
                contentImage = R.drawable.medal
            )
        }
    }
}

// Achievement and Inventory Item Card
@Composable
private fun UserCard(modifier: Modifier, contentName: String, contentImage: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RotatedCard(modifier = modifier, contentImage = contentImage)
        Text(
            modifier = modifier.padding(top = 24.dp),
            text = contentName,
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

// Achievement And Inventory Card
@Composable
private fun RotatedCard(modifier: Modifier, contentImage: Int) {
    Card(
        modifier = modifier
            .size(80.dp)
            .rotate(45f)
            .shadow(
                ambientColor = LightPurple,
                spotColor = LightPurple,
                elevation = 8.dp,
                shape = RoundedCornerShape(25)
            ),
        border = BorderStroke(width = 4.dp, color = Color.White),
        backgroundColor = WhiteSmoke,
        shape = RoundedCornerShape(25),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .rotate(-45f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = modifier.fillMaxSize(),
                painter = painterResource(id = contentImage),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}
