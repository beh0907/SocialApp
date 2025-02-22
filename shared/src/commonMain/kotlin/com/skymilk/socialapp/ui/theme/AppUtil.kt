package com.skymilk.socialapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember


@Composable
fun ProvideAppUtils(
    appDimens: Dimens,
    content: @Composable () -> Unit,
) {
    val rememberAppDimens = remember { appDimens }
    CompositionLocalProvider(LocalAppDimens provides rememberAppDimens) {
        content()
    }
}

val LocalAppDimens = compositionLocalOf {
    CompactDimens
}