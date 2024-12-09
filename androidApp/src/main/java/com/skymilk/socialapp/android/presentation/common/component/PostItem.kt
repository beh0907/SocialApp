package com.skymilk.socialapp.android.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.skymilk.socialapp.R
import com.skymilk.socialapp.android.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.android.ui.theme.LargeSpacing
import com.skymilk.socialapp.android.ui.theme.MediumSpacing
import com.skymilk.socialapp.domain.model.Post

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post,
    onClickPost: (Post) -> Unit,
    onNavigateToProfile: (Long) -> Unit,
    onLikeClick: (Post) -> Unit,
    isDetailScreen: Boolean = false
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { onClickPost(post) }
            .padding(bottom = ExtraLargeSpacing)
    ) {
        //헤더 유저 정보
        PostHeaderSection(
            name = post.userName,
            imageUrl = post.imageUrl,
            date = post.createdAt,
            onProfileClick = { onNavigateToProfile(post.userId) }
        )

        //메인 이미지
        SubcomposeAsyncImage(
            modifier = modifier
                .aspectRatio(1f)
                .fillMaxSize(),
            model = post.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = modifier
                        .aspectRatio(1f)
                        .fillMaxSize()
                        .shimmerEffect(RoundedCornerShape(0.dp))
                )
            }
        )

        //하단 정보
        PostFooterLikesSection(
            likesCount = post.likesCount,
            commentsCount = post.commentsCount,
            onLikeClick = { onLikeClick(post) },
            onCommentClick = { onNavigateToProfile(post.postId) },
            isPostLiked = post.isLiked
        )

        //게시물 설명
        Text(
            text = post.caption,
            modifier = Modifier.padding(horizontal = LargeSpacing),
            maxLines = if (isDetailScreen) 20 else 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun PostHeaderSection(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LargeSpacing, vertical = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {

        //프로필
        CircleImage(
            modifier = Modifier.size(30.dp),
            imageUrl = imageUrl,
            onClick = onProfileClick
        )

        //이름
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Box(
            Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        //날짜
        Text(
            modifier = Modifier.weight(1f),
            text = date,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.surfaceVariant,
                textAlign = TextAlign.Start
            ),
        )

        //더보기 버튼
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(R.drawable.round_more_horiz_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceVariant
            )
        }

    }
}

@Composable
fun PostFooterLikesSection(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentsCount: Int,
    isPostLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //좋아요 버튼
        IconButton(
            onClick = onLikeClick
        ) {
            Icon(
                painter = painterResource(
                    if (isPostLiked) R.drawable.like_icon_filled
                    else R.drawable.like_icon_outlined
                ),
                contentDescription = null,
//                tint = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        //좋아요 수
        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(MediumSpacing))


        //댓글 버튼
        IconButton(
            onClick = onCommentClick
        ) {
            Icon(
                painter = painterResource(R.drawable.chat_icon_outlined),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        //댓글 수
        Text(
            text = "$commentsCount",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}