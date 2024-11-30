package com.skymilk.socialapp.android.presentation.screen.main.profileEdit

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.CircleImage
import com.skymilk.socialapp.android.presentation.common.component.CustomTextField
import com.skymilk.socialapp.android.presentation.screen.main.profile.state.ProfileState
import com.skymilk.socialapp.android.presentation.screen.main.profileEdit.state.ProfileEditUiState
import com.skymilk.socialapp.android.ui.theme.ButtonHeight
import com.skymilk.socialapp.android.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.SmallElevation
import com.skymilk.socialapp.android.ui.theme.White

@Composable
fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileEditUiState,
    profileState: ProfileState,
    onEvent: (ProfileEditEvent) -> Unit,
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when(profileState) {
            is ProfileState.Loading -> CircularProgressIndicator()
            is ProfileState.Error -> {
                Column {
                    Text(
                        text = stringResource(R.string.could_not_load_profile),
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
                            text = stringResource(id = R.string.retry_button_text),
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
                            imageUrl = profileState.profile.profileUrl,
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
                                .clickable {}
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
                        hint = R.string.username_hint,
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
                        onClick = {
                            onEvent(ProfileEditEvent.UpdateProfile)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.profile_update_button_hint),
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
                text = stringResource(id = R.string.bio_hint),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}