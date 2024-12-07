package com.skymilk.socialapp.android.presentation.common.dummy

data class FollowsUser(
    val id: Long = -1,
    val name: String = "",
    val bio: String = "안녕하십니까, 저의 페이지에 오신 것을 환영합니다.",
    val profileUrl: String = "",
    val isFollowing: Boolean = false,
)

val sampleFollowsUser = listOf(
    FollowsUser(
        id = 1,
        name = "박사장",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        id = 2,
        name = "김펭귄",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        id = 3,
        name = "정상호",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        id = 4,
        name = "김창수",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
    FollowsUser(
        id = 5,
        name = "최선욱",
        profileUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ),
)