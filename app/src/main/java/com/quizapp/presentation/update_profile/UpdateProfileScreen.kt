package com.quizapp.presentation.update_profile

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.quizapp.R
import com.quizapp.core.common.getRealPathFromURI
import com.quizapp.core.common.loadImage
import com.quizapp.core.ui.component.CustomLoadingSpinner
import com.quizapp.core.ui.component.CustomTopBarTitle
import com.quizapp.core.ui.component.OtfCustom
import com.quizapp.core.ui.component.OutBtnCustom
import com.quizapp.presentation.utils.Dimens
import com.quizapp.presentation.utils.Messages
import com.quizapp.presentation.utils.UpdateProfileBottomSheetSubTitles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Todo: You must implement runtime permissons for the access media storage

private val EDIT_PROFILE_IMG_SIZE = 176.dp

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateProfileViewModel = hiltViewModel()
) {
    val updateProfileState by viewModel.updateProfileState.collectAsState()

    var showUploadImgSection by remember { mutableStateOf(false) }

    var selectedImgUri by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        selectedImgUri = it.toString()
        showUploadImgSection = true
        try {
            val filePath = it?.let { it1 -> getRealPathFromURI(it1, context) }
            if (filePath != null) {
                viewModel.handleMultipartBody(filePath)
            }
        } catch (e: Exception) {
            Log.e("image upload", e.stackTraceToString())
            Toast.makeText(
                context,
                com.quizapp.domain.utils.Messages.UNKNOWN,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    UpdateProfileScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        sheetState = sheetState,
        coroutineScope = coroutineScope,
        updateProfileState = updateProfileState,
        galleryLauncher = galleryLauncher,
        showUploadImgSection = showUploadImgSection,
        onSaveImgClick = {
            viewModel.uploadProfilePicture()
            showUploadImgSection = false
        },
        onCancelImgClick = { showUploadImgSection = false },
        selectedImgUri = selectedImgUri
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UpdateProfileScreenContent(
    modifier: Modifier,
    viewModel: UpdateProfileViewModel,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    updateProfileState: UpdateProfileState,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    showUploadImgSection: Boolean,
    onSaveImgClick: () -> Unit,
    onCancelImgClick: () -> Unit,
    selectedImgUri: String
) {
    if (showUploadImgSection) {
        UploadImageSection(
            modifier = modifier,
            profileImgUrl = selectedImgUri,
            onSaveImgClick = onSaveImgClick,
            onCancelImgClick = onCancelImgClick
        )
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            CustomTopBarTitle(
                modifier = modifier,
                title = "Update Profile"
            )
        }
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EditProfileSection(
                modifier = modifier,
                sheetState = sheetState,
                coroutineScope = coroutineScope,
                viewModel = viewModel,
                updateProfileState = updateProfileState,
                galleryLauncher = galleryLauncher
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EditProfileSection(
    modifier: Modifier,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
    viewModel: UpdateProfileViewModel,
    updateProfileState: UpdateProfileState,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    when (updateProfileState) {
        is UpdateProfileState.Nothing -> {
            ModalBottomSheetLayout(
                modifier = modifier.fillMaxSize(),
                sheetContent = {
                    SheetContent(
                        modifier = modifier,
                        viewModel = viewModel,
                        coroutineScope = coroutineScope,
                        sheetState = sheetState
                    )
                },
                sheetState = sheetState,
                sheetBackgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(top = Dimens.AppBarDefaultHeight + 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UpdateProfileImage(
                        modifier = modifier,
                        userImage = viewModel.profilePictureUrl,
                        galleryLauncher = galleryLauncher
                    )
                    Spacer(modifier = modifier.padding(vertical = 16.dp))
                    UpdateProfileInfo(
                        modifier = modifier,
                        iconId = R.drawable.ic_baseline_account_circle,
                        subTitle = UpdateProfileBottomSheetSubTitles.REAL_NAME,
                        value = viewModel.firstName + " " + viewModel.lastName,
                        onClick = {
                            coroutineScope.launch {
                                viewModel.setIsBiographySelected(false)
                                sheetState.show()
                            }
                        }
                    )
                    Divider(
                        modifier = modifier.fillMaxWidth(),
                        thickness = 1.dp,
                    )
                    UpdateProfileInfo(
                        modifier = modifier,
                        iconId = R.drawable.ic_baseline_info,
                        subTitle = UpdateProfileBottomSheetSubTitles.BIOGRAPHY,
                        value = viewModel.biography,
                        onClick = {
                            coroutineScope.launch {
                                viewModel.setIsBiographySelected(true)
                                sheetState.show()
                            }
                        }
                    )
                }
            }
        }
        is UpdateProfileState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CustomLoadingSpinner()
            }
        }
        is UpdateProfileState.Success -> {
            Toast.makeText(
                LocalContext.current,
                "Your profile successfully updated.",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetUpdateProfileState()
        }
        is UpdateProfileState.Error -> {
            Toast.makeText(
                LocalContext.current,
                updateProfileState.errorMessage,
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetUpdateProfileState()
        }
    }

}

@Composable
private fun UpdateProfileImage(
    modifier: Modifier,
    userImage: String,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        ProfileImage(
            modifier = modifier,
            userImage = userImage
        )
        Box(
            modifier = modifier.size(EDIT_PROFILE_IMG_SIZE),
            contentAlignment = Alignment.BottomEnd
        ) {
            IconButton(
                modifier = modifier
                    .background(color = MaterialTheme.colors.secondary, shape = CircleShape)
                    .clip(CircleShape),
                onClick = { galleryLauncher.launch("image/*") }
            ) {
                Icon(
                    modifier = modifier.padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_photo_camera),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

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
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant
            )
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
            tint = MaterialTheme.colors.secondary
        )
    }
}

@Composable
private fun UploadImageSection(
    modifier: Modifier,
    profileImgUrl: String,
    onSaveImgClick: () -> Unit,
    onCancelImgClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .weight(5f),
            model = loadImage(context = LocalContext.current, imageUrl = profileImgUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                OutBtnCustom(
                    modifier = Modifier.weight(1f),
                    onClick = onCancelImgClick,
                    buttonText = "Cancel"
                )
                OutBtnCustom(
                    modifier = Modifier.weight(1f),
                    onClick = onSaveImgClick,
                    buttonText = "Save"
                )
            }
        }
    }
}

@Composable
private fun ProfileImage(modifier: Modifier, userImage: String) {
    AsyncImage(
        modifier = modifier
            .size(EDIT_PROFILE_IMG_SIZE)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SheetContent(
    modifier: Modifier,
    viewModel: UpdateProfileViewModel,
    coroutineScope: CoroutineScope,
    sheetState: ModalBottomSheetState
) {
    if (viewModel.isBiographySelected) {
        BiographyBottomSheet(
            modifier = modifier,
            value = viewModel.biographyBottomSheet,
            onValueChanged = { viewModel.updateBiographyField(it) },
            onSaveClick = {
                viewModel.updateProfile()
                coroutineScope.launch {
                    sheetState.hide()
                }
            },
            onCancelClick = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        )
    } else {
        RealNameBottomSheet(
            modifier = modifier,
            onFirstNameChanged = { viewModel.updateFirstNameField(it) },
            onLastNameChanged = { viewModel.updateLastNameField(it) },
            firstNameValue = viewModel.firstNameBottomSheet,
            lastNameValue = viewModel.lastNameBottomSheet,
            onSaveClick = {
                viewModel.updateProfile()
                coroutineScope.launch {
                    sheetState.hide()
                }
            },
            onCancelClick = {
                coroutineScope.launch {
                    sheetState.hide()
                }
            }
        )
    }
}

@Composable
private fun BiographyBottomSheet(
    modifier: Modifier,
    onValueChanged: (String) -> Unit,
    value: String,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = UpdateProfileBottomSheetSubTitles.BIOGRAPHY,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = { onValueChanged(it) },
            placeHolderText = UpdateProfileBottomSheetSubTitles.BIOGRAPHY,
            value = value
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancelClick) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
                )
            }
            TextButton(onClick = onSaveClick) {
                Text(
                    text = "Save",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
private fun RealNameBottomSheet(
    modifier: Modifier,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    firstNameValue: String,
    lastNameValue: String,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = UpdateProfileBottomSheetSubTitles.REAL_NAME,
            style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colors.primaryVariant
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = { onFirstNameChanged(it) },
            placeHolderText = "First Name",
            value = firstNameValue
        )
        OtfCustom(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onValueChanged = { onLastNameChanged(it) },
            placeHolderText = "Last Name",
            value = lastNameValue
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancelClick) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
                )
            }
            TextButton(onClick = onSaveClick) {
                Text(
                    text = "Save",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
                )
            }
        }
    }
}