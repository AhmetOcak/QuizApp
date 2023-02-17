package com.quizapp.core.di

import com.quizapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.quizapp.data.datasource.remote.auth.AuthRemoteDataSourceImpl
import com.quizapp.data.datasource.remote.confirm_account.ConfirmAccountRemoteDataSource
import com.quizapp.data.datasource.remote.confirm_account.ConfirmAccountRemoteDataSourceImpl
import com.quizapp.data.datasource.remote.quiz.QuizRemoteDataSource
import com.quizapp.data.datasource.remote.quiz.QuizRemoteDataSourceImpl
import com.quizapp.data.datasource.remote.reset_password.ResetPasswordRemoteDataSource
import com.quizapp.data.datasource.remote.reset_password.ResetPasswordRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindQuizRemoteDataSource(quizRemoteDataSourceImpl: QuizRemoteDataSourceImpl): QuizRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindConfirmAccountRemoteDataSource(confirmAccountRemoteDataSourceImpl: ConfirmAccountRemoteDataSourceImpl): ConfirmAccountRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindResetPasswordRemoteDataSource(resetPasswordRemoteDataSourceImpl: ResetPasswordRemoteDataSourceImpl): ResetPasswordRemoteDataSource
}