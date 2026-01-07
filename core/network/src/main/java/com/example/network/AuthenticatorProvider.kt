package com.example.network

import okhttp3.Authenticator

interface AuthenticatorProvider {
    fun get(): Authenticator
}
