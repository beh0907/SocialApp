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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.skymilk.socialapp.android.R
import com.skymilk.socialapp.android.presentation.screen.destinations.HomeDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignInDestination
import com.skymilk.socialapp.android.presentation.screen.destinations.SignUpDestination
import com.skymilk.socialapp.android.ui.theme.SmallElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val currentDestination = navHostController.currentDestinationAsState().value

    Surface(
        shadowElevation = SmallElevation,
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            title = {
                Text(
                    text = stringResource(getAppBarTitle(currentDestination?.route)),
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                AnimatedVisibility(visible = currentDestination?.route == HomeDestination.route) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_circle_icon),
                            contentDescription = null
                        )
                    }
                }
            },
            navigationIcon = {
                if (!shouldShowNavigation(currentDestination?.route)) return@TopAppBar

                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_arrow_back),
                        contentDescription = null
                    )
                }
            }
        )
    }
}

private fun getAppBarTitle(currentDestinationRoute: String?): Int {
    return when (currentDestinationRoute) {
        SignInDestination.route -> R.string.signin_destination_title

        SignUpDestination.route -> R.string.signup_destination_title

        HomeDestination.route -> R.string.home_destination_title

        else -> R.string.no_destination_title
    }
}

private fun shouldShowNavigation(currentDestinationRoute: String?): Boolean {
    return false
}