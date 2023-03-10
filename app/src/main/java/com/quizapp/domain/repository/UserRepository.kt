package com.quizapp.domain.repository

import com.quizapp.domain.model.user.UpdatePasswordBody
import com.quizapp.domain.model.user.UpdateProfileBody
import com.quizapp.domain.model.user.UserProfile
import okhttp3.MultipartBody

interface UserRepository {

    suspend fun getUserProfile(token: String): UserProfile

    suspend fun updatePassword(token: String, updatePasswordBody: UpdatePasswordBody)

    suspend fun updateProfile(token: String, updateProfileBody: UpdateProfileBody)

    suspend fun uploadProfilePicture(token: String, file: MultipartBody.Part)

    suspend fun deleteAccount(userId: String)
}