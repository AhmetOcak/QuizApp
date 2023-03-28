package com.quizapp.data.repository

import com.quizapp.data.datasource.remote.user.UserRemoteDataSource
import com.quizapp.data.mappers.toLeaderboard
import com.quizapp.data.mappers.toUpdatePasswordBodyDto
import com.quizapp.data.mappers.toUpdateProfileBodyDto
import com.quizapp.data.mappers.toUserProfile
import com.quizapp.domain.model.user.Leaderboard
import com.quizapp.domain.model.user.UpdatePasswordBody
import com.quizapp.domain.model.user.UpdateProfileBody
import com.quizapp.domain.model.user.UserProfile
import com.quizapp.domain.repository.UserRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val remoteDataSource: UserRemoteDataSource) : UserRepository {

    override suspend fun getUserProfile(token: String): UserProfile =
        remoteDataSource.getUserProfile(token).toUserProfile()

    override suspend fun updatePassword(token: String, updatePasswordBody: UpdatePasswordBody) =
        remoteDataSource.updatePassword(
            token = token,
            updatePasswordBodyDto = updatePasswordBody.toUpdatePasswordBodyDto()
        )

    override suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody) =
        remoteDataSource.updateProfile(
            token = token,
            updateProfileBodyDto = updateProfileBody.toUpdateProfileBodyDto()
        )

    override suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part) =
        remoteDataSource.uploadProfilePicture(token = token, file = file)

    override suspend fun deleteAccount(userId: String) =
        remoteDataSource.deleteAccount(userId = userId)

    override suspend fun getLeaderboard(): ArrayList<Leaderboard> =
        remoteDataSource.getLeaderboard().toLeaderboard()
}