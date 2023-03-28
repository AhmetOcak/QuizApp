package com.quizapp.data.datasource.remote.user

import com.quizapp.data.datasource.remote.user.api.UserApi
import com.quizapp.data.datasource.remote.user.entity.LeaderboardDto
import com.quizapp.data.datasource.remote.user.entity.UpdatePasswordBodyDto
import com.quizapp.data.datasource.remote.user.entity.UpdateProfileBodyDto
import com.quizapp.data.datasource.remote.user.entity.UserProfileDto
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val api: UserApi) : UserRemoteDataSource {

    override suspend fun getUserProfile(token: String): UserProfileDto = api.getUserProfile(token)

    override suspend fun updatePassword(
        token: String,
        updatePasswordBodyDto: UpdatePasswordBodyDto
    ) = api.updatePassword(token = token, updatePasswordBodyDto = updatePasswordBodyDto)

    override suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto) =
        api.updateProfile(token = token, updateProfileBodyDto = updateProfileBodyDto)

    override suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part) =
        api.uploadProfilePicture(token = token, file = file)

    override suspend fun deleteAccount(userId: String) = api.deleteAccount(userId = userId)

    override suspend fun getLeaderboard(): ArrayList<LeaderboardDto> =
        api.getLeaderboard()
}