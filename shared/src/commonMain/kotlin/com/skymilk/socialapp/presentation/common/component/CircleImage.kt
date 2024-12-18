package com.skymilk.socialapp.presentation.common.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import com.skymilk.socialapp.SharedRes
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    image: Any?,
    onClick: () -> Unit
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() },
        model = image ?: painterResource(SharedRes.images.bg_default_profile),
        contentDescription = null,
        loading = {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .shimmerEffect(CircleShape)
            )
        },
        contentScale = ContentScale.Crop
    )
}