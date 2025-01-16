package com.skymilk.socialapp.store.presentation.screen.main.postCreate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Cancel
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.presentation.common.component.PostUploadImageItem
import com.skymilk.socialapp.store.presentation.screen.main.postCreate.state.PostCreateUiState
import com.skymilk.socialapp.ui.theme.ButtonHeight
import com.skymilk.socialapp.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.ui.theme.LargeSpacing
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun PostCreateScreen(
    modifier: Modifier = Modifier,
    uiState: PostCreateUiState,
    onEvent: (PostCreateEvent) -> Unit,
    onNavigateToBack: () -> Unit
) {
    //게시글 등록을 완료했다면 뒤로가기
    LaunchedEffect(uiState.isCreated) {
        if (uiState.isCreated) onNavigateToBack()
    }

    val scope = rememberCoroutineScope()
    val imagePicker = rememberImagePickerLauncher(
        //Multiple은 1이 반드시 초과해야 하기 때문에 1일땐 Single 모드로 전환해야 한다
        selectionMode = if (uiState.maxSelection == 1) SelectionMode.Single else SelectionMode.Multiple(
            maxSelection = uiState.maxSelection
        ),
        scope = scope,
        onResult = { selectedImages ->
            //선택된 이미지 전달
            onEvent(PostCreateEvent.AddImage(selectedImages))
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
        ) {
            PostCaptionTextField(
                caption = uiState.caption,
                onCaptionChange = {
                    onEvent(PostCreateEvent.UpdateCaption(it))
                }
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                //타이틀
                if (uiState.selectedImages.isEmpty()) {
                    item {
                        //선택된 이미지가 있다면 타이틀 제거
                        Text(
                            text = stringResource(SharedRes.strings.select_post_image_label),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                //선택된 이미지
                itemsIndexed(items = uiState.selectedImages) { index, item ->
                    PostUploadImageItem(
                        image = item,
                        onRemoveItem = {
                            onEvent(PostCreateEvent.RemoveImage(index = index))
                        }
                    )
                }

                //이미지 추가 버튼
                //추가로 선택할 수 이미지 수가 남아있는 경우에만 적용
                if (uiState.maxSelection > 0) {
                    item {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    imagePicker.launch()
                                }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(10.dp),
                                imageVector = Icons.Rounded.CameraAlt,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { onEvent(PostCreateEvent.CreatePost) },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(SharedRes.strings.create_post_button_label),
                    color = Color.White
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