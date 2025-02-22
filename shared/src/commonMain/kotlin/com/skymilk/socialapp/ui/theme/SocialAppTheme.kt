package com.skymilk.socialapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SocialAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors


    val window = calculateWindowSizeClass()
    var appDimens = CompactDimens
    var typography = provideCompactTypography()

    when (window.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            appDimens = CompactMediumDimens
            typography = provideCompactTypography()
        }

        WindowWidthSizeClass.Medium -> {
            appDimens = MediumDimens
            typography = provideMediumTypography()
        }

        WindowWidthSizeClass.Expanded -> {
            appDimens = ExpandedDimens
            typography = provideExpandedTypography()
        }
    }

    ProvideAppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}