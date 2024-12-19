package com.skymilk.socialapp.store.presentation.navigation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.skymilk.socialapp.MainAuthState
import com.skymilk.socialapp.store.presentation.common.component.AppBar
import com.skymilk.socialapp.store.presentation.navigation.routes.Routes
import com.skymilk.socialapp.store.presentation.screen.auth.signIn.SignIn
import com.skymilk.socialapp.store.presentation.screen.auth.signUp.SignUp
import com.skymilk.socialapp.store.presentation.screen.main.follows.Followers
import com.skymilk.socialapp.store.presentation.screen.main.follows.Following
import com.skymilk.socialapp.store.presentation.screen.main.home.Home
import com.skymilk.socialapp.store.presentation.screen.main.postCreate.PostCreate
import com.skymilk.socialapp.store.presentation.screen.main.postDetail.PostDetail
import com.skymilk.socialapp.store.presentation.screen.main.profile.Profile
import com.skymilk.socialapp.store.presentation.screen.main.profileEdit.ProfileEdit

@Composable
fun SocialApp(mainAuthState: MainAuthState) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    //플로팅 버튼 표시 여부
    val isVisibleFloating = remember(navBackStackEntry) {
        currentRoute == Routes.HomeScreen::class.qualifiedName
    }

    //로그인 정보 여부 체크
    when (mainAuthState) {
        is MainAuthState.Success -> {
            //토큰이 제거 되었다면 로그인 화면으로 이동
            LaunchedEffect(Unit) {
                if (mainAuthState.currentUser.token.isBlank()) {
                    navController.navigate(Routes.SignInScreen) {
                        popUpTo(Routes.HomeScreen) {
                            inclusive = true
                        }
                    }
                }
            }
        }

        else -> Unit
    }

    Scaffold(
        topBar = {
            //상단바 정의
            AppBar(navController = navController)
        },
        floatingActionButton = {
            //우측 하단의 플로팅 버튼
            AnimatedVisibility(isVisibleFloating) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        navController.navigate(Routes.PostCreateScreen)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = White
                    )
                }
            }
        },
//        snackbarHost =
    ) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Routes.MainGraph
        ) {

            navigation<Routes.AuthGraph>(
                startDestination = Routes.SignInScreen
            ) {

                composable<Routes.SignInScreen> {
                    SignIn(navigator = navController)
                }

                composable<Routes.SignUpScreen> {
                    SignUp(navigator = navController)
                }
            }

            navigation<Routes.MainGraph>(
                startDestination = Routes.HomeScreen
            ) {

                composable<Routes.HomeScreen> {
                    Home(navigator = navController)
                }

                composable<Routes.ProfileScreen> {
                    val args = it.toRoute<Routes.ProfileScreen>()

                    Profile(navigator = navController, userId = args.userId)
                }

                composable<Routes.ProfileEditScreen> {
                    val args = it.toRoute<Routes.ProfileEditScreen>()

                    ProfileEdit(navigator = navController, userId = args.userId)
                }

                composable<Routes.PostCreateScreen> {
                    PostCreate(navigator = navController)
                }

                composable<Routes.PostDetailScreen> {
                    val args = it.toRoute<Routes.PostDetailScreen>()
                    val userId = (mainAuthState as MainAuthState.Success).currentUser.id

                    PostDetail(navigator = navController, postId = args.postId, userId = userId)
                }

                composable<Routes.FollowingScreen> {
                    val args = it.toRoute<Routes.FollowingScreen>()

                    Following(navigator = navController, userId = args.userId)
                }

                composable<Routes.FollowersScreen> {
                    val args = it.toRoute<Routes.FollowersScreen>()

                    Followers(navigator = navController, userId = args.userId)
                }
            }

        }
    }
}