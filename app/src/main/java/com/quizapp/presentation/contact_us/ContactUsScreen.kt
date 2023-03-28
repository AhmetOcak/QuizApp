package com.quizapp.presentation.contact_us

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.presentation.utils.Dimens

@Composable
fun ContactUsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactUsViewModel = hiltViewModel()
) {

    ContactUsScreenContent(modifier = modifier, viewModel = viewModel)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ContactUsScreenContent(modifier: Modifier, viewModel: ContactUsViewModel) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { Title(modifier = modifier) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ContactUsSection(modifier = modifier)
        }
    }
}

@Composable
private fun ContactUsSection(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Description(modifier = modifier)
        Input(modifier = modifier)
        OutBtnCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            onClick = { },
            buttonText = "Send"
        )
    }
}

@Composable
private fun Description(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "Ask how we can help you:",
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.primaryVariant,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Input(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OtfCustom(
            modifier = modifier.fillMaxWidth(),
            onValueChanged = {},
            placeHolderText = "Name"
        )
        OtfCustom(
            modifier = modifier.fillMaxWidth(),
            onValueChanged = {},
            placeHolderText = "Subject"
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .height(144.dp),
            onValueChanged = {},
            placeHolderText = "Message"
        )
    }
}

@Composable
private fun Title(modifier: Modifier) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Contact Us",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primaryVariant,
                textAlign = TextAlign.Center
            )
        },
        backgroundColor = MaterialTheme.colors.background
    )
}