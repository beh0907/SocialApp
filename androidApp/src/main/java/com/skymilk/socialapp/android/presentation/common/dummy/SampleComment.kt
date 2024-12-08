package com.skymilk.socialapp.android.presentation.common.dummy

data class SampleComment(
    val id: Long,
    val comment: String,
    val date: String,
    val userName: String,
    val userImageUrl: String?,
    val userId: Long,
    val postId: Long
)

val sampleComments = listOf(
    SampleComment(
        id = 1,
        date = "2023-06-24",
        comment = "Great post!\nI learned a lot from it.",
        userName = sampleFollowsUser[0].name,
        userImageUrl = sampleFollowsUser[0].imageUrl,
        userId = sampleFollowsUser[0].id,
        postId = samplePosts[0].postId.hashCode().toLong()
    ),
    SampleComment(
        id = 2,
        date = "2023-06-24",
        comment = "Nice work! Keep sharing more content like this.",
        userName = sampleFollowsUser[1].name,
        userImageUrl = sampleFollowsUser[1].imageUrl,
        userId = sampleFollowsUser[1].id,
        postId = samplePosts[0].postId.hashCode().toLong()
    ),
    SampleComment(
        id = 3,
        date = "2023-06-24",
        comment = "Thanks for the insights!\nYour post was really helpful.",
        userName = sampleFollowsUser[2].name,
        userImageUrl = sampleFollowsUser[2].imageUrl,
        userId = sampleFollowsUser[2].id,
        postId = samplePosts[0].postId.hashCode().toLong()
    ),
    SampleComment(
        id = 4,
        date = "2023-06-24",
        comment = "I enjoyed reading your post! Looking forward to more.",
        userName = sampleFollowsUser[3].name,
        userImageUrl = sampleFollowsUser[3].imageUrl,
        userId = sampleFollowsUser[3].id,
        postId = samplePosts[0].postId.hashCode().toLong()
    )
)