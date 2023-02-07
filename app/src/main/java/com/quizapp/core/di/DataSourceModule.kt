package com.quizapp.core.di

import com.quizapp.data.datasource.remote.auth.AuthRemoteDataSource
import com.quizapp.data.datasource.remote.auth.AuthRemoteDataSourceImpl
import com.quizapp.data.datasource.remote.quiz.QuizRemoteDataSource
import com.quizapp.data.datasource.remote.quiz.QuizRemoteDataSourceImpl
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
}