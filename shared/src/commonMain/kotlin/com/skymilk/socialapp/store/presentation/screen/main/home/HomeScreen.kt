package com.skymilk.socialapp.store.presentation.screen.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import app.cash.paging.compose.LazyPagingItems
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.domain.model.FollowsUser
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.store.presentation.common.component.postsList
import com.skymilk.socialapp.store.presentation.screen.main.home.component.OnBoardingUserList
import com.skymilk.socialapp.store.presentation.screen.main.home.state.OnBoardingState
import com.skymilk.socialapp.ui.theme.LargeSpacing
import com.skymilk.socialapp.ui.theme.MediumSpacing
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBoardingState: OnBoardingState,
    feedPosts: LazyPagingItems<Post>,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToProfile: (Long) -> Unit,
    onNavigateToPostDetail: (Post) -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        isRefreshing = (onBoardingState is OnBoardingState.Loading) /*|| (feedPosts.loadState.source.isIdle)*/,
        onRefresh = { onEvent(HomeEvent.RetryData) }
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            //온보딩 처리
            if (onBoardingState is OnBoardingState.Success) {
                item(key = "onBoarding") {
                    AnimatedVisibility(
                        visible = onBoardingState.shouldShowOnBoarding,
                        exit = shrinkVertically()
                    ) {
                        OnBoardingSection(
                            users = onBoardingState.users,
                            onNavigateToProfile = { onNavigateToProfile(it.id) },
                            onFollowClick = { user ->
                                onEvent(HomeEvent.FollowUser(user))
                            },
                            onBoardingDismiss = { onEvent(HomeEvent.DismissOnBoarding) }
                        )
                    }
                }

            }

            //피드 게시물 목록
            postsList(
                posts = feedPosts,
                onClickPost = onNavigateToPostDetail,
                onNavigateToProfile = onNavigateToProfile,
                onLikeClick = { onEvent(HomeEvent.LikePost(it)) }
            )
        }
    }

}

@Composable
fun OnBoardingSection(
    modifier: Modifier = Modifier,
    users: List<FollowsUser> = emptyList(),
    onNavigateToProfile: (FollowsUser) -> Unit,
    onFollowClick: (FollowsUser) -> Unit,
    onBoardingDismiss: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LargeSpacing),
            text = stringResource(SharedRes.strings.onboarding_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(MediumSpacing))

        OnBoardingUserList(
            users = users,
            onNavigateToProfile = onNavigateToProfile,
            onFollowClick = onFollowClick
        )

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = LargeSpacing),
            onClick = onBoardingDismiss,
            shape = RoundedCornerShape(percent = 50)
        ) {
            Text(
                text = stringResource(SharedRes.strings.onboarding_finish_button),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}