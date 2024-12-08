package com.skymilk.socialapp.android.presentation.common.dummy

import com.skymilk.socialapp.domain.model.Post
import com.skymilk.socialapp.util.DateFormatter

data class SamplePost(
    val id: Long,
    val text: String,
    val imageUrl: String,
    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val userId: Long,
    val userName: String,
    val userImageUrl: String,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false,
) {
    fun toPost(): Post {
        return Post(
            postId = id,
            caption = text,
            imageUrl = imageUrl,
            createdAt = DateFormatter.parseDate(createdAt),
            likesCount = likesCount,
            commentsCount = commentsCount,
            userId = userId,
            userName = userName,
            userImageUrl = userImageUrl,
            isLiked = isLiked,
            isOwnPost = isOwnPost
        )
    }
}

val samplePosts = listOf(
    SamplePost(
        id = 11,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "20 min",
        likesCount = 12,
        commentsCount = 3,
        userId = 1,
        userName = "Mr Dip",
        userImageUrl = "https://picsum.photos/200"
    ).toPost(),
    SamplePost(
        id = 12,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "May 03, 2023",
        likesCount = 121,
        commentsCount = 23,
        userId = 2,
        userName = "John Cena",
        userImageUrl = "https://picsum.photos/200"
    ).toPost(),
    SamplePost(
        id = 13,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "Apr 12, 2023",
        likesCount = 221,
        commentsCount = 41,
        userId = 3,
        userName = "Cristiano",
        userImageUrl = "https://picsum.photos/200"
    ).toPost(),
    SamplePost(
        id = 14,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "Mar 31, 2023",
        likesCount = 90,
        commentsCount = 13,
        userId = 3,
        userName = "Cristiano",
        userImageUrl = "https://picsum.photos/200"
    ).toPost(),
    SamplePost(
        id = 15,
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "Jan 30, 2023",
        likesCount = 121,
        commentsCount = 31,
        userId = 4,
        userName = "L. James",
        userImageUrl = "https://picsum.photos/200"
    ).toPost(),
)