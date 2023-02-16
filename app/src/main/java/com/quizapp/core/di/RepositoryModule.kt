package com.quizapp.core.di

import com.quizapp.data.repository.AuthRepositoryImpl
import com.quizapp.data.repository.ConfirmAccountRepositoryImpl
import com.quizapp.data.repository.QuizRepositoryImpl
import com.quizapp.domain.repository.AuthRepository
import com.quizapp.domain.repository.ConfirmAccountRepository
import com.quizapp.domain.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuizRepository(quizRepositoryImpl: QuizRepositoryImpl): QuizRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindConfirmAccountRepository(confirmAccountRepositoryImpl: ConfirmAccountRepositoryImpl): ConfirmAccountRepository
}