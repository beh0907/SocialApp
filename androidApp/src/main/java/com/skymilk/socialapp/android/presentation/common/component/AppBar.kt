package com.skymilk.socialapp.android.presentation.common.component

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.navigation.routes.Routes
import com.skymilk.socialapp.android.ui.theme.SmallElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val canGoBack = remember(navBackStackEntry) {
        navController.previousBackStackEntry != null
    }

    val isVisibleProfile = remember(navBackStackEntry) {
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
                        painter = painterResource(id = R.drawable.round_arrow_back),
                        contentDescription = null
                    )
                }
            },
            actions = {
                AnimatedVisibility(!isVisibleProfile) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_circle_icon),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

// 현재 화면의 라우트 설명을 반환하는 확장 함수
fun getCurrentRouteDescription(navBackStackEntry : NavBackStackEntry?): Int {
    val currentRoute = navBackStackEntry?.destination?.route

    return when {
        // 인증 관련 화면 (파라미터 없는 화면)
        currentRoute == Routes.SignInScreen::class.qualifiedName -> R.string.signin_destination_title
        currentRoute == Routes.SignUpScreen::class.qualifiedName -> R.string.signup_destination_title
        currentRoute == Routes.HomeScreen::class.qualifiedName -> R.string.home_destination_title
        currentRoute == Routes.PostCreateScreen::class.qualifiedName -> R.string.post_create_destination_title

        // 파라미터가 있는 화면들
        currentRoute?.startsWith(Routes.ProfileScreen::class.qualifiedName.toString()) == true -> {
            R.string.profile_destination_title
        }
        currentRoute?.startsWith(Routes.ProfileEditScreen::class.qualifiedName.toString()) == true -> {
            R.string.profile_edit_destination_title
        }
        currentRoute?.startsWith(Routes.FollowersScreen::class.qualifiedName.toString()) == true -> {
            R.string.followers_title
        }
        currentRoute?.startsWith(Routes.FollowingScreen::class.qualifiedName.toString()) == true -> {
            R.string.following_title
        }
        currentRoute?.startsWith(Routes.PostDetailScreen::class.qualifiedName.toString()) == true -> {
            R.string.post_detail_destination_title
        }

        // 기본 처리
        else -> R.string.no_destination_title
    }
}