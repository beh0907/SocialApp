package com.skymilk.socialapp.android.presentation.common.dummy

data class Comment(
    val id: String,
    val comment: String,
    val date: String,
    val authorName: String,
    val authorImageUrl: String,
    val authorId: Int,
    val postId: Long
)

val sampleComments = listOf(
    Comment(
        id = "comment1",
        date = "2023-06-24",
        comment = "Great post!\nI learned a lot from it.",
        authorName = sampleFollowsUser[0].name,
        authorImageUrl = sampleFollowsUser[0].profileUrl,
        authorId = sampleFollowsUser[0].id,
        postId = samplePosts[0].id.hashCode().toLong()
    ),
    Comment(
        id = "comment2",
        date = "2023-06-24",
        comment = "Nice work! Keep sharing more content like this.",
        authorName = sampleFollowsUser[1].name,
        authorImageUrl = sampleFollowsUser[1].profileUrl,
        authorId = sampleFollowsUser[1].id,
        postId = samplePosts[0].id.hashCode().toLong()
    ),
    Comment(
        id = "comment3",
        date = "2023-06-24",
        comment = "Thanks for the insights!\nYour post was really helpful.",
        authorName = sampleFollowsUser[2].name,
        authorImageUrl = sampleFollowsUser[2].profileUrl,
        authorId = sampleFollowsUser[2].id,
        postId = samplePosts[0].id.hashCode().toLong()
    ),
    Comment(
        id = "comment4",
        date = "2023-06-24",
        comment = "I enjoyed reading your post! Looking forward to more.",
        authorName = sampleFollowsUser[3].name,
        authorImageUrl = sampleFollowsUser[3].profileUrl,
        authorId = sampleFollowsUser[3].id,
        postId = samplePosts[0].id.hashCode().toLong()
    )
)