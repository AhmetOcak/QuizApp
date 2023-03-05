package com.quizapp.data.datasource.remote.user

import com.quizapp.data.datasource.remote.user.api.UserApi
import com.quizapp.data.datasource.remote.user.entity.UpdateProfileBodyDto
import com.quizapp.data.datasource.remote.user.entity.UserProfileDto
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val api: UserApi) : UserRemoteDataSource {

    override suspend fun getUserProfile(token: String): UserProfileDto = api.getUserProfile(token)

    override suspend fun updateProfile(token: String, updateProfileBodyDto: UpdateProfileBodyDto) =
        api.updateProfile(token = token, updateProfileBodyDto = updateProfileBodyDto)

}