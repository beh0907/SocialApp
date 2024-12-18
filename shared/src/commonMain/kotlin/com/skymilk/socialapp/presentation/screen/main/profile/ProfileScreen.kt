package com.skymilk.socialapp.presentation.screen.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.compose.LazyPagingItems
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.domain.model.Profile
import com.skymilk.socialapp.presentation.common.component.CircleImage
import com.skymilk.socialapp.presentation.common.component.FollowsButton
import com.skymilk.socialapp.presentation.common.component.postsList
import com.skymilk.socialapp.presentation.screen.main.profile.state.ProfileState
import com.skymilk.socialapp.ui.theme.LargeSpacing
import com.skymilk.socialapp.ui.theme.MediumSpacing
import com.skymilk.socialapp.ui.theme.SmallSpacing
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileState: ProfileState,
    feedPosts: LazyPagingItems<Post>,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToFollowers: () -> Unit,
    onNavigateToFollowing: () -> Unit,
    onNavigateToPostDetail: (Post) -> Unit,
) {
    when (profileState) {
        is ProfileState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProfileState.Success -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                item("header") {
                    HeaderSection(
                        profile = profileState.profile,
                        onFollowersClick = onNavigateToFollowers,
                        onFollowingClick = onNavigateToFollowing,
                        onButtonClick = {
                            //팔로우 버튼 클릭
                            if (profileState.profile.isOwnProfile) onNavigateToProfileEdit() //내 정보일땐 정보 수정 화면으로 이동
                            else onEvent(ProfileEvent.FollowUser(profileState.profile)) // 다른 회원이라면 팔로우 처리
                        }
                    )
                }

                //피드 게시물 목록
                postsList(
                    posts = feedPosts,
                    onClickPost = onNavigateToPostDetail,
                    onNavigateToProfile = { },
                    onLikeClick = { onEvent(ProfileEvent.LikePost(it)) }
                )
            }
        }

        else -> Unit
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    profile: Profile,
    isCurrentUser: Boolean = false,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = MediumSpacing)
            .background(MaterialTheme.colorScheme.surface)
            .padding(LargeSpacing),
    ) {
        CircleImage(
            modifier = Modifier.size(90.dp),
            image = profile.imageUrl,
            onClick = {}
        )

        Spacer(modifier = Modifier.height(SmallSpacing))

        Text(
            text = profile.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = profile.bio,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(MediumSpacing))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                FollowText(
                    count = profile.followersCount,
                    title = stringResource(SharedRes.strings.followers_title),
                    onClick = onFollowersClick
                )

                Spacer(modifier = Modifier.width(MediumSpacing))

                FollowText(
                    count = profile.followingCount,
                    title = stringResource(SharedRes.strings.following_title),
                    onClick = onFollowingClick
                )
            }

            FollowsButton(
                modifier = Modifier.width(100.dp),
                text = if (profile.isOwnProfile) stringResource(SharedRes.strings.edit_button_text)
                else if (profile.isFollowing) stringResource(SharedRes.strings.unfollow_button_text)
                else stringResource(SharedRes.strings.follow_button_text),
                onFollowClick = onButtonClick,
                isOutline = isCurrentUser || profile.isFollowing
            )
        }
    }
}

@Composable
fun FollowText(
    modifier: Modifier = Modifier,
    count: Int,
    title: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                ) {
                    append(text = "$count ")
                }


                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                ) {
                    append(text = title)
                }
            }
        )
    }
}