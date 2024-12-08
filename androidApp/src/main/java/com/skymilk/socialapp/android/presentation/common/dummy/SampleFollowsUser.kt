package com.skymilk.socialapp.android.presentation.common.dummy

import com.skymilk.socialapp.domain.model.FollowsUser


data class SampleFollowsUser(
    val id: Long = -1,
    val name: String = "",
    val bio: String = "안녕하십니까, 저의 페이지에 오신 것을 환영합니다.",
    val imageUrl: String = "",
    val isFollowing: Boolean = false,
) {
    fun toFollowUser(): FollowsUser {
        return FollowsUser(id, name, bio, imageUrl, isFollowing)
    }
}

val sampleFollowsUser = listOf(
    SampleFollowsUser(
        id = 1,
        name = "박사장",
        imageUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ).toFollowUser(),
    SampleFollowsUser(
        id = 2,
        name = "김펭귄",
        imageUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ).toFollowUser(),
    SampleFollowsUser(
        id = 3,
        name = "정상호",
        imageUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ).toFollowUser(),
    SampleFollowsUser(
        id = 4,
        name = "김창수",
        imageUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ).toFollowUser(),
    SampleFollowsUser(
        id = 5,
        name = "최선욱",
        imageUrl = "https://picsum.photos/200/200?random=1" // 랜덤 이미지
    ).toFollowUser(),
)