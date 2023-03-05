package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.user.UserRemoteDataSource
import com.quizapp.data.mappers.toUpdateProfileBodyDto
import com.quizapp.data.mappers.toUserProfile
import com.quizapp.domain.model.user.UpdateProfileBody
import com.quizapp.domain.model.user.UserProfile
import com.quizapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val remoteDataSource: UserRemoteDataSource) : UserRepository {

    override suspend fun getUserProfile(token: String): UserProfile =
        remoteDataSource.getUserProfile(token).toUserProfile()

    override suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody) =
        remoteDataSource.updateProfile(token = token, updateProfileBodyDto = updateProfileBody.toUpdateProfileBodyDto())

}