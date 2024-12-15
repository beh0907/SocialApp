package com.skymilk.socialapp.android.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.skymilk.socialapp.android.MainAuthState
import com.skymilk.socialapp.android.presentation.common.component.AppBar
import com.skymilk.socialapp.android.presentation.navigation.routes.Routes
import com.skymilk.socialapp.android.presentation.screen.NavGraphs
import com.skymilk.socialapp.android.presentation.screen.auth.signIn.SignIn2
import com.skymilk.socialapp.android.presentation.screen.auth.signUp.SignUp2
import com.skymilk.socialapp.android.presentation.screen.destinations.HomeDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.PostCreateDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignInDestination
import com.skymilk.socialapp.android.presentation.screen.main.follows.Followers2
import com.skymilk.socialapp.android.presentation.screen.main.follows.Following2
import com.skymilk.socialapp.android.presentation.screen.main.home.Home2
import com.skymilk.socialapp.android.presentation.screen.main.postCreate.PostCreate2
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.PostDetail2
import com.skymilk.socialapp.android.presentation.screen.main.profile.Profile2
import com.skymilk.socialapp.android.presentation.screen.main.profileEdit.ProfileEdit2
import com.skymilk.socialapp.android.ui.theme.White

@Composable
fun SocialApp(mainAuthState: MainAuthState) {
    val navHostController = rememberNavController()

    when (mainAuthState) {
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
        floatingActionButton = {
            AnimatedVisibility(
                navHostController.currentDestinationAsState().value == HomeDestination
            ) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navHostController.navigate(PostCreateDestination.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = White
                    )
                }
            }
        }
    ) { innerPadding ->
        DestinationsNavHost(
            modifier = Modifier.padding(innerPadding),
            navGraph = NavGraphs.root,
            navController = navHostController
        )
    }
}

@Composable
fun SocialApp2(mainAuthState: MainAuthState) {
    val navHostController = rememberNavController()

    when (mainAuthState) {
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
        floatingActionButton = {
            AnimatedVisibility(
                navHostController.currentDestinationAsState().value == Routes.HomeScreen
            ) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navHostController.navigate(PostCreateDestination.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = White
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navHostController,
            startDestination = Routes.MainGraph
        ) {

            navigation<Routes.AuthGraph>(
                startDestination = Routes.SignInScreen
            ) {

                composable<Routes.SignInScreen> {
                    SignIn2(navigator = navHostController)
                }

                composable<Routes.SignUpScreen> {
                    SignUp2(navigator = navHostController)
                }
            }

            navigation<Routes.MainGraph>(
                startDestination = Routes.HomeScreen
            ) {

                composable<Routes.HomeScreen> {
                    Home2(navigator = navHostController)
                }

                composable<Routes.ProfileScreen> {
                    val args = it.toRoute<Routes.ProfileScreen>()

                    Profile2(navigator = navHostController, userId = args.userId)
                }

                composable<Routes.ProfileEditScreen> {
                    val args = it.toRoute<Routes.ProfileEditScreen>()

                    ProfileEdit2(navigator = navHostController, userId = args.userId)
                }

                composable<Routes.PostCreateScreen> {
                    PostCreate2(navigator = navHostController)
                }

                composable<Routes.PostDetailScreen> {
                    val args = it.toRoute<Routes.PostDetailScreen>()

                    PostDetail2(navigator = navHostController, postId = args.postId)
                }

                composable<Routes.FollowingScreen> {
                    val args = it.toRoute<Routes.FollowingScreen>()

                    Following2(navigator = navHostController, userId = args.userId)
                }

                composable<Routes.FollowersScreen> {
                    val args = it.toRoute<Routes.FollowersScreen>()

                    Followers2(navigator = navHostController, userId = args.userId)
                }
            }

        }

    }
}