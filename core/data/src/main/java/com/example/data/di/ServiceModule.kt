package com.example.data.di

import com.example.data.service.AuthService
import com.example.data.service.DummyService
import com.example.data.service.PostService
import com.example.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideDummyService(
        retrofit: Retrofit,
    ): DummyService = retrofit.create(DummyService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit,
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(
        retrofit: Retrofit,
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun providePostService(
        retrofit: Retrofit,
    ): PostService = retrofit.create(PostService::class.java)
}
