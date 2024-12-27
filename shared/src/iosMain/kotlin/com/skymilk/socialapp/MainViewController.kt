package com.skymilk.socialapp

import androidx.compose.ui.window.ComposeUIViewController
import com.skymilk.socialapp.store.presentation.navigation.SocialApp

fun MainViewController() = ComposeUIViewController { SocialApp() }