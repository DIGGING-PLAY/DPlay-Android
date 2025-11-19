package com.example.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Recommend : Route

    @Serializable
    data object MyPage : Route
}