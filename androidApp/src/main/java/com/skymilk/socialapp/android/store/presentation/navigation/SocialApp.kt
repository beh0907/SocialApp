package com.skymilk.socialapp.android.store.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.skymilk.socialapp.android.store.presentation.screen.NavGraphs

@Composable
fun SocialApp() {
    val navHostController = rememberNavController()

    DestinationsNavHost(navGraph = NavGraphs.root, navController = navHostController)
}