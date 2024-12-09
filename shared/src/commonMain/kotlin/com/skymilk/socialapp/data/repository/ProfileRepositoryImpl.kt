package com.skymilk.socialapp.data.repository

import com.skymilk.socialapp.data.local.UserPreferences
import com.skymilk.socialapp.data.mapper.toDomainProfile
import com.skymilk.socialapp.data.mapper.toUserSettings
import com.skymilk.socialapp.data.remote.ProfileApiService
import com.skymilk.socialapp.domain.model.Profile
import com.skymilk.socialapp.domain.repository.ProfileRepository
import com.skymilk.socialapp.util.DispatcherProvider
import com.skymilk.socialapp.util.Result
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
}