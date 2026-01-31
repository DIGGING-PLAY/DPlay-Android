package com.example.otherprofile

import com.example.domain.model.LoadingState
import com.example.ui.base.BaseContract

class OtherProfileContract {
    data class OtherProfileState(
        val loadingState: LoadingState = LoadingState.LOADING,
        val userNickname: String = "디플레이",
        val registeredMusicCount: Int = 0,
        val profileImagePath: String? = null,
        val selectedTabIndex: Int = 0,
    ) : BaseContract.State

    sealed interface OtherProfileIntent : BaseContract.Intent {
        data object OnBackIconClick : OtherProfileIntent

        data class OnTabClick(
            val tabIndex: Int,
        ) : OtherProfileIntent

        data class OnScrappedTrackClick(
            val postId: Long,
        ) : OtherProfileIntent

        data class OnRegisteredTrackClick(
            val postId: Long,
        ) : OtherProfileIntent
    }

    sealed interface OtherProfileSideEffect : BaseContract.SideEffect {
        data class NavigateToDetail(
            val postId: Long,
        ) : OtherProfileSideEffect

        data object NavigateToBack : OtherProfileSideEffect
    }
}
