package com.quizapp.presentation.contact_us

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom

@Composable
fun ContactUsScreen(modifier: Modifier = Modifier) {

    ContactUsScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ContactUsScreenContent(modifier: Modifier) {
    Scaffold(
        topBar = {
            MyTopAppBar()
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DescriptionSection()
            InputSection(modifier = modifier)
            OutBtnCustom(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                onClick = { /*TODO*/ },
                buttonText = "Send"
            )
        }
    }
}

@Composable
private fun MyTopAppBar() {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primaryVariant
                )
            }
        },
        title = {
            Text(
                text = "Contact Us!",
                style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.primaryVariant
            )
        },
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    )
}

@Composable
private fun DescriptionSection() {
    Text(
        text = "You can send an email to us whenever you want !!!",
        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun InputSection(modifier: Modifier) {
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