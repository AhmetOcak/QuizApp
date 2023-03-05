package com.quizapp.presentation.edit_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.quizapp.R
import com.quizapp.core.common.loadImage
import com.quizapp.core.ui.component.CustomTopBarTitle
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom

//Todo: Profile img size will set

private val EDIT_PROFILE_IMG_SIZE = 176.dp
private val SETTINGS_PROFILE_IMG_SIZE = 108.dp
private val PREFERENCES_HEIGHT = 56.dp

enum class Sections {
    SETTINGS,
    PROFILE,
    CHANGE_PASSWORD
}

//Todo: PreferenceOnClickActions will be implement

enum class PreferenceOnClickActions {
    INFORMATION,
    CHANGE_PASSWORD,
    LOG_OUT,
    NOTHING
}

//Todo: Edit icon color and update profile img button will change

@Composable
fun EditProfileScreen(modifier: Modifier = Modifier) {

    EditProfileScreenContent(
        modifier = modifier,
        userName = "Ahmet",
        userImage = "",
        firstName = "Ahmet",
        lastName = "Ocak",
        onEditProfileClick = {}
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun EditProfileScreenContent(
    modifier: Modifier,
    userName: String,
    userImage: String,
    firstName: String,
    lastName: String,
    onEditProfileClick: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { CustomTopBarTitle(modifier = modifier, title = "Settings") }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileSection(
                modifier = modifier,
                userName = userName,
                userImage = userImage,
                firstName = firstName,
                lastName = lastName,
                onEditProfileClick = onEditProfileClick
            )
            //EditProfileSection(modifier = modifier, userImage = userImage)
            //ChangePasswordSection(modifier = modifier)
            //ContactUsSection(modifier = modifier)
        }
    }
}

@Composable
private fun ProfileSection(
    modifier: Modifier,
    userName: String,
    userImage: String,
    firstName: String,
    lastName: String,
    onEditProfileClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            ProfileImage(modifier = modifier, userImage = userImage)
            UserRealNameAndUserName(
                modifier = modifier,
                userName = userName,
                firstName = firstName,
                lastName = lastName
            )
            EditProfileButton(modifier = modifier, onEditProfileClick = onEditProfileClick)
        }

        Preferences(modifier = modifier.weight(1f))
    }
}

// Created for ProfileSection
@Composable
private fun UserRealNameAndUserName(
    modifier: Modifier,
    userName: String,
    firstName: String,
    lastName: String
) {
    Column(
        modifier = modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$firstName $lastName",
            style = MaterialTheme.typography.h2.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colors.primaryVariant
        )
        Text(
            text = "@$userName",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

// Created for ProfileSection
@Composable
private fun EditProfileButton(modifier: Modifier, onEditProfileClick: () -> Unit) {
    TextButton(
        modifier = modifier.padding(top = 16.dp),
        onClick = onEditProfileClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Blue)
    ) {
        Text(
            modifier = modifier.padding(horizontal = 8.dp),
            text = "Edit Profile >",
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

// Created for ProfileSection
@Composable
private fun Preferences(modifier: Modifier) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
            )
            .clip(RoundedCornerShape(topStartPercent = 10, topEndPercent = 10))
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        preferenceList.forEach {
            Preference(
                iconId = it.iconId,
                text = it.text,
                onPreferenceClick = it.onPreferenceClick,
                action = it.action
            )
        }
    }
}

@Composable
private fun Preference(
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String,
    onPreferenceClick: () -> Unit,
    action: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(PREFERENCES_HEIGHT)
            .clickable(onClick = onPreferenceClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = iconId), contentDescription = null)
            Text(
                modifier = modifier.padding(start = 16.dp),
                text = text,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        action()
    }
}

@Composable
private fun EditProfileSection(modifier: Modifier, userImage: String) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UpdateProfileImage(modifier = modifier, userImage = userImage)
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        UpdateProfileInfo(
            modifier = modifier,
            iconId = R.drawable.ic_baseline_account_circle,
            subTitle = "FirstName",
            value = "Ahmet",
            onClick = {}
        )
        Divider(
            modifier = modifier.fillMaxWidth(),
            thickness = 1.dp
        )
        UpdateProfileInfo(
            modifier = modifier,
            iconId = R.drawable.ic_baseline_account_circle,
            subTitle = "LastName",
            value = "Ocak",
            onClick = {}
        )
        Divider(
            modifier = modifier.fillMaxWidth(),
            thickness = 1.dp,
        )
        UpdateProfileInfo(
            modifier = modifier,
            iconId = R.drawable.ic_baseline_info,
            subTitle = "Bioraphy",
            value = "DSADSA dsadsadsa dsasad",
            onClick = {}
        )
    }
}

// Created for EditProfileSection
@Composable
private fun UpdateProfileImage(modifier: Modifier, userImage: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        ProfileImage(modifier = modifier, userImage = userImage)
        Box(
            modifier = modifier.size(EDIT_PROFILE_IMG_SIZE),
            contentAlignment = Alignment.BottomEnd
        ) {
            IconButton(
                modifier = modifier
                    .background(color = Color.Blue, shape = CircleShape)
                    .clip(CircleShape),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = modifier.padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_photo_camera),
                    contentDescription = null
                )
            }
        }
    }
}


// Created for ProfileSection and EditProfileSection
@Composable
private fun ProfileImage(modifier: Modifier, userImage: String) {
    AsyncImage(
        modifier = modifier
            .size(SETTINGS_PROFILE_IMG_SIZE)
            .clip(CircleShape)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primaryVariant
                ), shape = CircleShape
            ),
        model = loadImage(context = LocalContext.current, imageUrl = userImage),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

// Created for EditProfileSection
@Composable
private fun UpdateProfileInfo(
    modifier: Modifier,
    iconId: Int,
    subTitle: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 32.dp)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = iconId), contentDescription = null)
            Column(
                modifier = modifier.padding(start = 32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = subTitle,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colors.primaryVariant,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Icon(
            modifier = modifier.padding(end = 16.dp),
            painter = painterResource(id = R.drawable.ic_baseline_edit),
            contentDescription = null,
            tint = Color.Blue
        )
    }
}

@Composable
private fun ChangePasswordSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OtfCustom(
            modifier = modifier.fillMaxWidth(),
            onValueChanged = {},
            placeHolderText = "Old Password",
            keyboardType = KeyboardType.Password
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = {},
            placeHolderText = "New Password",
            keyboardType = KeyboardType.Password
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = {},
            placeHolderText = "Confirm New Password",
            keyboardType = KeyboardType.Password
        )
        OutBtnCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            onClick = { /*TODO*/ },
            buttonText = "Submit"
        )
    }
}

@Composable
private fun ContactUsSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Description()
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

// Created for ContactUsSection
@Composable
private fun Description() {
    Text(
        text = "You can send an email to us whenever you want !!!",
        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primaryVariant
    )
}

// Created for ContactUsSection
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

data class PreferenceModel(
    val iconId: Int,
    val text: String,
    val onPreferenceClick: () -> Unit,
    val action: @Composable RowScope.() -> Unit
)

private val preferenceList = listOf(
    PreferenceModel(
        iconId = R.drawable.ic_baseline_language,
        text = "Language",
        onPreferenceClick = {}
    ) {
        Text(
            text = "English >",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    /*PreferenceModel(
        iconId = R.drawable.ic_outline_dark_mode,
        text = "Dark Mode",
        onPreferenceClick = {}
    ) {
        Switch(checked = true, onCheckedChange = {})
    },*/
    PreferenceModel(
        iconId = R.drawable.ic_baseline_info,
        text = "Information",
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_password,
        text = "Change Password",
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_headphones,
        text = "Contact Us",
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    },
    PreferenceModel(
        iconId = R.drawable.ic_baseline_logout,
        text = "Log Out",
        onPreferenceClick = {}
    ) {
        Text(
            text = ">",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primaryVariant
        )
    }
)