package com.skymilk.socialapp.android.presentation.screen.main.profile

import androidx.annotation.StringRes
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.presentation.common.component.CircleImage
import com.skymilk.socialapp.android.presentation.common.component.FollowsButton
import com.skymilk.socialapp.android.presentation.common.component.PostItem
import com.skymilk.socialapp.android.presentation.common.dummy.Post
import com.skymilk.socialapp.android.presentation.common.dummy.Profile
import com.skymilk.socialapp.android.presentation.common.state.PostsState
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.HeaderSection
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.component.CommentItem
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.CommentsState
import com.skymilk.socialapp.android.presentation.screen.main.postDetail.state.PostState
import com.skymilk.socialapp.android.presentation.screen.main.profile.state.ProfileState
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing
import com.skymilk.socialapp.android.ui.theme.SmallSpacing

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileState: ProfileState,
    postsState: PostsState,
    onEvent: (ProfileEvent) -> Unit,
    onProfileClick: () -> Unit,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onPostClick: (Post) -> Unit,
    onLikeClick: (String) -> Unit,
    onCommentClick: (String) -> Unit,
) {
    when {
        profileState is ProfileState.Loading || postsState is PostsState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        profileState is ProfileState.Success && postsState is PostsState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            ) {
                item("header") {
                    HeaderSection(
                        profile = profileState.profile,
                        onFollowersClick = onFollowersClick,
                        onFollowingClick = onFollowingClick,
                        onFollowClick = onProfileClick
                    )
                }

                items(items = postsState.posts, key = { post -> post.id }) {
                    PostItem(
                        post = it,
                        onPostClick = onPostClick,
                        onProfileClick = { },
                        onLikeClick = onLikeClick,
                        onCommentClick = onCommentClick,
                    )
                }
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
    isFollowing: Boolean = false,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onFollowClick: () -> Unit,
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
            imageUrl = profile.profileUrl,
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
                    title = R.string.followers_title,
                    onClick = onFollowersClick
                )

                Spacer(modifier = Modifier.width(MediumSpacing))

                FollowText(
                    count = profile.followingCount,
                    title = R.string.following_title,
                    onClick = onFollowingClick
                )
            }

            FollowsButton(
                text = R.string.follow_button_text,
                onFollowClick = onFollowClick,
                isOutline = isCurrentUser || isFollowing
            )
        }
    }
}

@Composable
fun FollowText(
    modifier: Modifier = Modifier,
    count: Int,
    @StringRes title: Int,
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
                    append(text = stringResource(title))
                }
            }
        )
    }
}