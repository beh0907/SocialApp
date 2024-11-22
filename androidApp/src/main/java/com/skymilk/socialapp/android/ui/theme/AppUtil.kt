package com.skymilk.socialapp.android.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

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


val ScreenOrientation
    @Composable
    get() = LocalConfiguration.current.orientation