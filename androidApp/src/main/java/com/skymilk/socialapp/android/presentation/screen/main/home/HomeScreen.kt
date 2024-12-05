package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.TransformOrigin.Companion
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.PostItem
import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.Post
import com.skymilk.socialapp.android.presentation.common.state.PostsState
import com.skymilk.socialapp.android.presentation.screen.main.home.component.OnBoardingUserList
import com.skymilk.socialapp.android.presentation.screen.main.home.state.OnBoardingState
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing
import java.lang.System.exit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBoardingState: OnBoardingState,
    postsState: PostsState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToPost: (Post) -> Unit,
    onNavigateToProfile: (Int) -> Unit,
    onLikeClick: (String) -> Unit,
    onCommentClick: (String) -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = (onBoardingState is OnBoardingState.Loading) && (postsState is PostsState.Loading),
        onRefresh = { onEvent(HomeEvent.RetryData) }
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            if (onBoardingState is OnBoardingState.Success) {
                item(key = "onBoarding") {
                    AnimatedVisibility(
                        visible = onBoardingState.shouldShowOnBoarding,
                        exit = shrinkVertically()
                    ) {
                        OnBoardingSection(
                            users = onBoardingState.users,
                            onUserClick = { onNavigateToProfile(it.id) },
                            onFollowClick = { user, isFollow ->
                                onEvent(HomeEvent.FollowUser(user, isFollow))
                            },
                            onBoardingDismiss = {
                                onEvent(HomeEvent.DismissOnBoarding)
                            }
                        )
                    }
                }

            }

            if ((postsState is PostsState.Success)) {
                items(items = postsState.posts, key = { post -> post.id }) {
                    PostItem(
                        post = it,
                        onNavigateToPost = onNavigateToPost,
                        onNavigateToProfile = onNavigateToProfile,
                        onLikeClick = onLikeClick,
                        onCommentClick = onCommentClick,
                    )
                }
            }
        }
    }

}

@Composable
fun OnBoardingSection(
    modifier: Modifier = Modifier,
    users: List<FollowsUser> = emptyList(),
    onUserClick: (FollowsUser) -> Unit,
    onFollowClick: (FollowsUser, Boolean) -> Unit,
    onBoardingDismiss: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MediumSpacing),
            text = stringResource(R.string.onboarding_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LargeSpacing),
            text = stringResource(R.string.onboarding_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(MediumSpacing))

        OnBoardingUserList(users = users, onUserClick = onUserClick, onFollowClick = onFollowClick)

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = LargeSpacing),
            onClick = onBoardingDismiss,
            shape = RoundedCornerShape(percent = 50)
        ) {
            Text(text = stringResource(R.string.onboarding_finish_button))
        }
    }
}