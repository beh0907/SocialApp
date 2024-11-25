package com.skymilk.socialapp.android.presentation.common.dummy

data class FollowsUser(
    val seq: Int = -1,
    val name: String = "",
    val profileUrl: String = "",
    val isFollowing: Boolean = false,
)

val sampleFollowsUser = listOf(
    FollowsUser(
        seq = 1,
        name = "박사장",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        seq = 2,
        name = "김펭귄",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        seq = 3,
        name = "정상호",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        seq = 4,
        name = "김창수",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        seq = 5,
        name = "최선욱",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
)