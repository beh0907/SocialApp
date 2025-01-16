package com.skymilk.socialapp.store.presentation.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun PostUploadImageItem(
    modifier: Modifier = Modifier,
    image: Any, // String 타입의 Url이나 ByteArray 타입의 이미지
    onRemoveItem: () -> Unit
) {

    Box(
        modifier = Modifier.size(80.dp)
    ) {
        //이미지
        CoilImage(
            imageModel = { image },
            modifier = modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        )

        //선택 이미지 삭제 버튼
        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopEnd)
                .padding(2.dp)
                .clip(CircleShape)
                .clickable {
                    //이미지 제거
                    onRemoveItem()
                },
            imageVector = Icons.Rounded.Cancel,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}