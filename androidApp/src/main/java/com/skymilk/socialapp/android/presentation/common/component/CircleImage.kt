package com.skymilk.socialapp.android.presentation.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.SubcomposeAsyncImage

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onClick: () -> Unit
) {
    SubcomposeAsyncImage(
        modifier = modifier.clip(CircleShape).clickable { onClick() },
        model = imageUrl,
        contentDescription = null,
        loading = {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .shimmerEffect(CircleShape)
            )
        }
    )
}