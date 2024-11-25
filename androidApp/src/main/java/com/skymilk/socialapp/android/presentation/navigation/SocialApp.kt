package com.skymilk.socialapp.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.skymilk.socialapp.android.presentation.common.component.AppBar
import com.skymilk.socialapp.android.presentation.screen.NavGraphs
import com.skymilk.socialapp.android.presentation.screen.destinations.HomeDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignInDestination

@Composable
fun SocialApp(token: String?) {
    val navHostController = rememberNavController()

    //토큰이 제거 되었다면 로그인 화면으로 이동
    LaunchedEffect(token) {
        if (token != null && token.isBlank()) {
            navHostController.navigate(SignInDestination.route) {
                popUpTo(HomeDestination.route) {
                    inclusive = true
                }
            }
        }

    }

    Scaffold(
        topBar = { AppBar(navHostController = navHostController) },
    ) { innerPadding ->
        DestinationsNavHost(
            modifier = Modifier.padding(innerPadding),
            navGraph = NavGraphs.root,
            navController = navHostController
        )
    }

}