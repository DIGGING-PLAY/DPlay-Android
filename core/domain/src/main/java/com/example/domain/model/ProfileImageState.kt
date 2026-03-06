package com.example.domain.model

sealed interface ProfileImageState {
    data object Keep : ProfileImageState

    data object Delete : ProfileImageState

    data class Update(val imagePath: String) : ProfileImageState
}