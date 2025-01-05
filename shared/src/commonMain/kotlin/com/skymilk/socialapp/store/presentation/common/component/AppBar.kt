package com.skymilk.socialapp.store.presentation.common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.skymilk.socialapp.MainAuthState
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.presentation.navigation.routes.Routes
import com.skymilk.socialapp.ui.theme.SmallElevation
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    mainAuthState: MainAuthState.Success?,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val canGoBack = remember(key1 = navBackStackEntry) {
        navController.previousBackStackEntry != null
    }

    val isVisibleProfile = remember(key1 = navBackStackEntry) {
        navBackStackEntry?.destination?.route == Routes.SignInScreen::class.qualifiedName ||
                navBackStackEntry?.destination?.route == Routes.SignUpScreen::class.qualifiedName
    }

    Surface(
        shadowElevation = SmallElevation,
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            title = {
                Text(
                    text = stringResource(getCurrentRouteDescription(navBackStackEntry)),
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                if (!canGoBack) return@TopAppBar

                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                AnimatedVisibility(!isVisibleProfile) {
                    IconButton(onClick = {
                        mainAuthState?.currentUser?.id?.let {
                            navController.navigate(Routes.ProfileScreen(it)){
                                popUpTo(Routes.HomeScreen) {
                                    inclusive = false
                                }
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(SharedRes.images.person_circle_icon),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

// 현재 화면의 라우트 설명을 반환하는 확장 함수
fun getCurrentRouteDescription(navBackStackEntry: NavBackStackEntry?): StringResource {
    val currentRoute = navBackStackEntry?.destination?.route

    return when {
        //파라미터 없는 화면
        currentRoute == Routes.SignInScreen::class.qualifiedName -> SharedRes.strings.signin_destination_title
        currentRoute == Routes.SignUpScreen::class.qualifiedName -> SharedRes.strings.signup_destination_title
        currentRoute == Routes.HomeScreen::class.qualifiedName -> SharedRes.strings.home_destination_title
        currentRoute == Routes.PostCreateScreen::class.qualifiedName -> SharedRes.strings.post_create_destination_title

        // 파라미터가 있는 화면들
        currentRoute?.startsWith(Routes.ProfileScreen::class.qualifiedName.toString()) == true -> {
            SharedRes.strings.profile_destination_title
        }

        currentRoute?.startsWith(Routes.ProfileEditScreen::class.qualifiedName.toString()) == true -> {
            SharedRes.strings.profile_edit_destination_title
        }

        currentRoute?.startsWith(Routes.FollowersScreen::class.qualifiedName.toString()) == true -> {
            SharedRes.strings.followers_title
        }

        currentRoute?.startsWith(Routes.FollowingScreen::class.qualifiedName.toString()) == true -> {
            SharedRes.strings.following_title
        }

        currentRoute?.startsWith(Routes.PostDetailScreen::class.qualifiedName.toString()) == true -> {
            SharedRes.strings.post_detail_destination_title
        }

        currentRoute?.startsWith(Routes.PostEditScreen::class.qualifiedName.toString()) == true -> {
            SharedRes.strings.post_detail_destination_title
        }

        // 기본 처리
        else -> SharedRes.strings.no_destination_title
    }
}