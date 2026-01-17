package com.example.data.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.DummyRepositoryImpl
import com.example.data.repository.PostRepositoryImpl
import com.example.data.repository.QuestionRepositoryImpl
import com.example.data.repository.TrackRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DummyRepository
import com.example.domain.repository.PostRepository
import com.example.domain.repository.QuestionRepository
import com.example.domain.repository.TrackRepository
import com.example.domain.repository.UserRepository
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
    abstract fun bindsDummyRepository(repositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindsPostRepository(repositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun bindsQuestionRepository(repositoryImpl: QuestionRepositoryImpl): QuestionRepository

    @Binds
    @Singleton
    abstract fun bindsTrackRepository(repositoryImpl: TrackRepositoryImpl): TrackRepository
}
