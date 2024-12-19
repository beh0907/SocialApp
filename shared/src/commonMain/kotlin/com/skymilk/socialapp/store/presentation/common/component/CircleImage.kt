package com.skymilk.socialapp.store.presentation.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.skymilk.socialapp.SharedRes
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    image: Any?,
    onClick: () -> Unit
) {
    image?.let {
        CoilImage(
            imageModel = { it },
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick() },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            loading = {
                Box(
                    modifier = modifier
                        .aspectRatio(1f)
                        .fillMaxSize()
                        .shimmerEffect(CircleShape)
                )
            }
        )
    } ?: run {
        Image(
            painter = painterResource(SharedRes.images.bg_default_profile),
            contentDescription = null,
            modifier = modifier
                .clip(CircleShape)
                .clickable { onClick() }
        )
    }
}