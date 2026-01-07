package com.example.data

import com.example.network.AuthenticatorProvider
import okhttp3.Authenticator
import javax.inject.Inject
import javax.inject.Provider

class AuthenticatorProviderImpl @Inject constructor(
    private val tokenAuthenticator: Provider<TokenAuthenticator>
) : AuthenticatorProvider {
    override fun get(): Authenticator = tokenAuthenticator.get()
}