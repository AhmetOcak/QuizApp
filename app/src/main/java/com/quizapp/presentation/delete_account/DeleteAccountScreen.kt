package com.quizapp.presentation.delete_account

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.quizapp.R
import com.quizapp.core.navigation.NavScreen
import com.quizapp.core.navigation.Navigator
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.core.ui.theme.FailRed
import com.quizapp.presentation.utils.DeleteAccountWarningMessage

@Composable
fun DeleteAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: DeleteAccountViewModel = hiltViewModel()
) {

    val deleteAccountState by viewModel.deleteAccountState.collectAsState()

    DeleteAccountScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        deleteAccountState = deleteAccountState
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun DeleteAccountScreenContent(
    modifier: Modifier,
    viewModel: DeleteAccountViewModel,
    deleteAccountState: DeleteAccountState
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { Title(modifier = modifier) }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (deleteAccountState) {
                is DeleteAccountState.Nothing -> {
                    WarningImageSection(modifier = modifier)
                    WarningMessageSection(modifier = modifier)
                    DeleteButton(modifier = modifier) { viewModel.deleteAccount() }
                }
                is DeleteAccountState.Loading -> {
                    CustomLoadingSpinner()
                }
                is DeleteAccountState.Success -> {
                    ShowMessage(message = "Your account deleted successfully.")
                    Navigator.navigate(NavScreen.SignInScreen.route) { popUpTo(0) }
                }
                is DeleteAccountState.Error -> {
                    ShowMessage(message = deleteAccountState.errorMessage)
                    viewModel.resetDeleteAccountState()
                }
            }
        }
    }
}

@Composable
private fun WarningImageSection(modifier: Modifier) {
    Image(
        modifier = modifier.size(LocalConfiguration.current.screenWidthDp.dp / 2),
        painter = painterResource(id = R.drawable.delete_account),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun WarningMessageSection(modifier: Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        text = DeleteAccountWarningMessage.WARNING_MESSAGE,
        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.primaryVariant,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun DeleteButton(modifier: Modifier, onDelete: () -> Unit) {
    OutBtnCustom(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        onClick = onDelete,
        buttonText = "Delete Account",
        backgroundColor = FailRed,
        textColor = MaterialTheme.colors.onPrimary
    )
}

@Composable
private fun Title(modifier: Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = "Delete Account",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.SemiBold),
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun ShowMessage(message: String) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_LONG
    ).show()
}