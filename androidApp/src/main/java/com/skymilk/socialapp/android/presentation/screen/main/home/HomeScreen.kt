package com.skymilk.socialapp.android.presentation.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.dummy.FollowsUser
import com.skymilk.socialapp.android.presentation.common.dummy.Post
import com.skymilk.socialapp.android.presentation.screen.main.home.component.OnBoardingUserList
import com.skymilk.socialapp.android.presentation.screen.main.home.component.PostItem
import com.skymilk.socialapp.android.presentation.screen.main.home.state.OnBoardingUiState
import com.skymilk.socialapp.android.presentation.screen.main.home.state.PostsUiState
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBoardingUiState: OnBoardingUiState,
    postsUiState: PostsUiState,
    onEvent: (HomeEvent) -> Unit,
    onPostClick: (Post) -> Unit,
    onProfileClick: (Int) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = onBoardingUiState.isLoading && postsUiState.isLoading,
        onRefresh = { onEvent(HomeEvent.RetryData) }
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            if (onBoardingUiState.shouldShowOnBoarding) {
                item(key = "onBoarding") {
                    OnBoardingSection(
                        users = onBoardingUiState.users,
                        onUserClick = { onProfileClick(it.seq) },
                        onFollowClick = { user, isFollow ->
                            onEvent(HomeEvent.FollowUser(user, isFollow))
                        },
                        onBoardingFinish = {
                            onEvent(HomeEvent.DismissOnBoarding)
                        }
                    )
                }

            }

            items(items = postsUiState.posts, key = { post -> post.id }) {
                PostItem(
                    post = it,
                    onPostClick = onPostClick,
                    onProfileClick = onProfileClick,
                    onLikeClick = onLikeClick,
                    onCommentClick = onCommentClick,
                )
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
    onBoardingFinish: () -> Unit
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
            onClick = onBoardingFinish,
            shape = RoundedCornerShape(percent = 50)
        ) {
            Text(text = stringResource(R.string.onboarding_finish_button))
        }
    }
}