package com.quizapp.core.di

import com.quizapp.data.repository.*
import com.quizapp.domain.repository.*
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

    @Binds
    @Singleton
    abstract fun bindResetPasswordRepository(resetPasswordRepositoryImpl: ResetPasswordRepositoryImpl): ResetPasswordRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindQuestionRepository(questionRepositoryImpl: QuestionRepositoryImpl): QuestionRepository

    @Binds
    @Singleton
    abstract fun bindOptionRepository(optionRepositoryImpl: OptionRepositoryImpl): OptionRepository

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(categoriesRepositoryImpl: CategoriesRepositoryImpl): CategoriesRepository
}