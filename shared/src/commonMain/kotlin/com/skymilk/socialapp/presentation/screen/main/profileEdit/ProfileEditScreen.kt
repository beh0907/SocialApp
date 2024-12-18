package com.skymilk.socialapp.presentation.screen.main.profileEdit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.presentation.common.component.CircleImage
import com.skymilk.socialapp.presentation.common.component.CustomTextField
import com.skymilk.socialapp.presentation.screen.main.profile.state.ProfileState
import com.skymilk.socialapp.presentation.screen.main.profileEdit.state.ProfileEditUiState
import com.skymilk.socialapp.ui.theme.ButtonHeight
import com.skymilk.socialapp.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.ui.theme.LargeSpacing
import com.skymilk.socialapp.ui.theme.SmallElevation
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileEditUiState,
    profileState: ProfileState,
    onEvent: (ProfileEditEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onEvent(ProfileEditEvent.UpdateImage(it))
            }
        }
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (profileState) {
            is ProfileState.Loading -> CircularProgressIndicator()
            is ProfileState.Error -> {
                Column {
                    Text(
                        text = stringResource(SharedRes.strings.could_not_load_profile),
                        style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Center)
                    )


                    Button(
                        modifier = Modifier.height(ButtonHeight),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp
                        ),
                        shape = MaterialTheme.shapes.medium,
                        onClick = {
                            onEvent(ProfileEditEvent.RetryData)
                        }
                    ) {
                        Text(
                            text = stringResource(SharedRes.strings.retry_button_text),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            is ProfileState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(ExtraLargeSpacing),
                    verticalArrangement = Arrangement.spacedBy(LargeSpacing),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //프로필 이미지
                    Box {
                        CircleImage(
                            modifier = Modifier.size(120.dp),
                            image = uiState.imageBytes ?: profileState.profile.imageUrl,
                            onClick = { }
                        )

                        Icon(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .shadow(
                                    elevation = SmallElevation,
                                    shape = RoundedCornerShape(percent = 25)
                                )
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(percent = 25)
                                )
                                .size(40.dp)
                                .clickable { singleImagePicker.launch() }
                                .padding(8.dp),
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(Modifier.height(LargeSpacing))

                    CustomTextField(
                        value = uiState.name,
                        onValueChange = { onEvent(ProfileEditEvent.UpdateName(it)) },
                        hint = stringResource(SharedRes.strings.username_hint),
                    )

                    BioTextField(
                        value = uiState.bio,
                        onValueChange = { onEvent(ProfileEditEvent.UpdateBio(it)) },
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(ButtonHeight),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp
                        ),
                        shape = MaterialTheme.shapes.medium,
                        onClick = { onEvent(ProfileEditEvent.UpdateProfile) }
                    ) {
                        Text(
                            text = stringResource(SharedRes.strings.profile_update_button_hint),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }
                }
            }

            else -> Unit
        }
    }

}

@Composable
fun BioTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(
                text = stringResource(SharedRes.strings.bio_hint),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}