package com.skymilk.socialapp.store.presentation.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skymilk.socialapp.ui.theme.SmallSpacing

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SmallSpacing),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}