package com.skymilk.socialapp.android.store.presentation.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Destination
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