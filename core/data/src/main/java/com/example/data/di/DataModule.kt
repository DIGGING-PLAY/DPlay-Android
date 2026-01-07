package com.example.data.di

import com.example.data.TokenManagerImpl
import com.example.network.TokenManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Binds
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindTokenProvider(
        tokenManagerImpl: TokenManagerImpl
    ): TokenManager
}