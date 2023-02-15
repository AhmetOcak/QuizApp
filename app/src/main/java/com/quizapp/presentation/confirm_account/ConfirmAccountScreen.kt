package com.quizapp.presentation.confirm_account

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.ui.component.OutBtnCustom

@Composable
fun ConfirmAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: ConfirmAccountViewModel = hiltViewModel()
) {

    ConfirmAccountScreenContent(modifier = modifier, viewModel = viewModel)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ConfirmAccountScreenContent(modifier: Modifier, viewModel: ConfirmAccountViewModel) {
    Scaffold(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageSection(modifier = modifier)
            ContentSection(modifier = modifier)
            ActivateButtonSection(modifier = modifier)
        }
    }
}

@Composable
private fun ImageSection(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(180.dp)
            .clip(CircleShape)
            .background(color = Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = modifier.size(128.dp),
            painter = painterResource(id = R.drawable.ic_baseline_check),
            contentDescription = null,
            tint = MaterialTheme.colors.background
        )
    }
}

@Composable
private fun ContentSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Account Activation",
            style = MaterialTheme.typography.h1.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primaryVariant
            )
        )
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = "Hi. Welcome to QuizApp, to activate your account, just click the button below.",
            style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.primaryVariant),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ActivateButtonSection(modifier: Modifier) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = { /*TODO*/ },
        buttonText = "Activate Account"
    )
}










