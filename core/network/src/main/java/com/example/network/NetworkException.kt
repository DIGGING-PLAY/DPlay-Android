package com.example.network

class NetworkException(
    val code: Int,
    override val message: String
) : Exception(message)
