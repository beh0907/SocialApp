package com.skymilk.socialapp.store.data.repository

import com.skymilk.socialapp.store.data.local.UserPreferences
import com.skymilk.socialapp.store.data.mapper.toDomainProfile
import com.skymilk.socialapp.store.data.mapper.toUserSettings
import com.skymilk.socialapp.store.data.model.UpdateProfileParams
import com.skymilk.socialapp.store.data.remote.ProfileApiService
import com.skymilk.socialapp.store.data.util.Result
import com.skymilk.socialapp.store.data.util.safeApiRequest
import com.skymilk.socialapp.store.domain.model.Profile
import com.skymilk.socialapp.store.domain.repository.ProfileRepository
import com.skymilk.socialapp.util.DispatcherProvider
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

internal class ProfileRepositoryImpl(
    private val profileApiService: ProfileApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : ProfileRepository {
    override suspend fun getProfile(profileId: Long): Flow<Result<Profile>> {
        return flow {
            val userData = userPreferences.getUserData()

            //선택한 유저가 나일 경우
            if (profileId == userData.id) emit(Result.Success(userData.toDomainProfile()))

            //유저 정보 요청
            val response = profileApiService.getProfile(
                token = userData.token,
                profileId = profileId,
                currentUserId = userData.id
            )

            when (response.code) {
                HttpStatusCode.OK -> {
                    val profile = response.data.profile!!.toProfile()

                    //내 정보는 데이터스토어에 갱신
                    if (profileId == userData.id)
                        userPreferences.setUserData(profile.toUserSettings(userData.token))

                    emit(Result.Success(profile))
                }

                else -> emit(Result.Error(message = "오류 : ${response.data.message}"))
            }
        }.catch {
            emit(Result.Error(message = "오류 : ${it.message}"))
        }.flowOn(dispatcher.io)
    }

    override suspend fun updateProfile(
        profile: Profile,
        imageBytes: ByteArray?
    ): Result<Profile> {
        return safeApiRequest(dispatcher) {
            val userData = userPreferences.getUserData()
            val profileData = Json.encodeToString(
                serializer = UpdateProfileParams.serializer(),
                value = UpdateProfileParams(
                    userId = profile.id,
                    name = profile.name,
                    bio = profile.bio,
                    fileName = profile.imageUrl?.substringAfterLast('/') //파일명만 추출한다
                )
            )

            //유저 정보 갱신 요청
            val response = profileApiService.updateProfile(
                token = userData.token,
                profileData = profileData,
                imageBytes = imageBytes
            )

            if (response.code == HttpStatusCode.OK) {
                var imageUrl = profile.imageUrl

                //업로드한 프로필 이미지가 있다면 경로 요청
                if (imageBytes != null) {
                    val updatedProfileResponse = profileApiService.getProfile(
                        token = userData.token,
                        profileId = profile.id,
                        currentUserId = profile.id
                    )
                    //프로필 이미지 경로 설정
                    updatedProfileResponse.data.profile?.let {
                        imageUrl = it.fileName
                    }
                }

                //데이터 스토어에 유저 정보 저장
                val updatedProfile = profile.copy(imageUrl = imageUrl)
                userPreferences.setUserData(updatedProfile.toUserSettings(userData.token))

                //최종 갱신 정보 리턴
                Result.Success(data = updatedProfile)
            } else {
                Result.Error(message = "${response.data.message}")
            }
        }
    }
}