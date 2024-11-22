package com.skymilk.socialapp.android.store.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Home Screen",
            modifier = Modifier.fillMaxWidth()
        )

    }

}