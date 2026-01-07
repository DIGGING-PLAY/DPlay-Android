package com.example.data.di

import com.example.data.networkimpl.AuthenticatorProviderImpl
import com.example.data.networkimpl.TokenManagerImpl
import com.example.network.AuthenticatorProvider
import com.example.network.TokenManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Binds
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindTokenManager(
        tokenManagerImpl: TokenManagerImpl
    ): TokenManager

    @Binds
    @Singleton
    abstract fun bindAuthenticatorProvider(
        provider: AuthenticatorProviderImpl
    ): AuthenticatorProvider
}