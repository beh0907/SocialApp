package com.skymilk.socialapp.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.skymilk.socialapp.android.MainAuthState
import com.skymilk.socialapp.android.presentation.common.component.AppBar
import com.skymilk.socialapp.android.presentation.screen.NavGraphs
import com.skymilk.socialapp.android.presentation.screen.destinations.HomeDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignInDestination
import com.skymilk.socialapp.data.model.UserSettings

@Composable
fun SocialApp(mainAuthState: MainAuthState) {
    val navHostController = rememberNavController()

    when(mainAuthState) {
        is MainAuthState.Success -> {
            //토큰이 제거 되었다면 로그인 화면으로 이동
            LaunchedEffect(Unit) {
                if (mainAuthState.currentUser.token.isBlank()) {
                    navHostController.navigate(SignInDestination.route) {
                        popUpTo(HomeDestination.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        else -> Unit
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