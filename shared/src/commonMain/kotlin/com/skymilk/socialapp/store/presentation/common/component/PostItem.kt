package com.skymilk.socialapp.store.presentation.common.component

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import com.skymilk.socialapp.SharedRes
import com.skymilk.socialapp.store.domain.model.Post
import com.skymilk.socialapp.ui.theme.ExtraLargeSpacing
import com.skymilk.socialapp.ui.theme.LargeSpacing
import com.skymilk.socialapp.ui.theme.MediumSpacing
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post,
    onClickPost: (Post) -> Unit,
    onClickPostMore: () -> Unit,
    onNavigateToProfile: (Long) -> Unit,
    onLikeClick: (Post) -> Unit,
    isDetailScreen: Boolean = false
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .clickable(enabled = !isDetailScreen) { onClickPost(post) }
            .padding(bottom = ExtraLargeSpacing)
    ) {
        //헤더 유저 정보
        PostHeaderSection(
            name = post.userName,
            imageUrl = post.userImageUrl,
            date = post.createdAt,
            isOwnPost = post.isOwnPost,
            onProfileClick = { onNavigateToProfile(post.userId) },
            onClickPostMore = onClickPostMore,
            isDetailScreen = isDetailScreen
        )

        //메인 이미지
        CoilImage(
            imageModel = { post.imageUrl },
            modifier = modifier
                .aspectRatio(1f)
                .fillMaxSize(),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
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
            onCommentClick = { if (!isDetailScreen) onClickPost(post) },
            isPostLiked = post.isLiked
        )

        //게시글 설명
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
    imageUrl: String?,
    date: String,
    isOwnPost: Boolean,
    onProfileClick: () -> Unit,
    onClickPostMore: () -> Unit,
    isDetailScreen: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(LargeSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {

        //프로필
        CircleImage(
            modifier = Modifier.size(30.dp),
            image = imageUrl,
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
                .background(MaterialTheme.colorScheme.onSurface)
        )

        //날짜
        Text(
            modifier = Modifier.weight(1f),
            text = date,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            ),
        )

        //더보기 버튼
        //상세 화면에서만 처리할 수 있다
        if (isDetailScreen && isOwnPost) {
            Icon(
                modifier = Modifier.clip(CircleShape).clickable { onClickPostMore() },
                imageVector = Icons.Rounded.MoreHoriz,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
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
                    if (isPostLiked) SharedRes.images.like_icon_filled
                    else SharedRes.images.like_icon_outlined
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
                painter = painterResource(SharedRes.images.chat_icon_outlined),
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