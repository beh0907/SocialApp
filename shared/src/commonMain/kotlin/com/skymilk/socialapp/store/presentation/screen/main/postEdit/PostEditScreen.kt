package com.skymilk.socialapp.store.presentation.screen.main.postEdit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.presentation.screen.main.postEdit.state.PostEditUiState
import com.skymilk.socialapp.ui.theme.ButtonHeight
import com.skymilk.socialapp.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.ui.theme.LargeSpacing
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PostEditScreen(
    modifier: Modifier = Modifier,
    uiState: PostEditUiState,
    onEvent: (PostEditEvent) -> Unit,
    onNavigateToBack: () -> Unit
) {
    //게시글 갱신이 완료했다면 뒤로가기
    LaunchedEffect(uiState.isUpdated) {
        if (uiState.isUpdated) onNavigateToBack()
    }

    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onEvent(PostEditEvent.UpdateImage(it))
            }
        }
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(ExtraLargeSpacing),
            verticalArrangement = Arrangement.spacedBy(LargeSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PostCaptionTextField(
                caption = uiState.caption,
                onCaptionChange = {
                    onEvent(PostEditEvent.UpdateCaption(it))
                }
            )

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(SharedRes.strings.select_post_image_label),
                    style = MaterialTheme.typography.bodySmall
                )

                //선택된 이미지가 없을 때 원본 이미지를 사용한다
                CoilImage(
                    imageModel = { uiState.imageBytes ?: uiState.imageUrl },
                    modifier = modifier
                        .size(70.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            singleImagePicker.launch()
                        },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )
            }

            Button(
                onClick = { onEvent(PostEditEvent.EditPost) },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(SharedRes.strings.edit_post_button_label),
                    color = White
                )
            }
        }
    }
}

@Composable
private fun PostCaptionTextField(
    modifier: Modifier = Modifier,
    caption: String,
    onCaptionChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        value = caption,
        onValueChange = onCaptionChange,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = {
            Text(
                text = stringResource(SharedRes.strings.post_caption_hint),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}