package com.example.ui.base

sealed interface BaseContract {
    interface State : BaseContract

    interface Intent : BaseContract

    interface SideEffect : BaseContract
}