package com.example.detail

import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.domain.model.BADGE
import com.example.domain.model.Like
import com.example.domain.model.Track
import com.example.domain.model.Writer
import com.example.ui.base.BaseContract

class DetailContract {
    data class DetailState(
        val postId: Long = 0L,
        val isScrapped: Boolean = false,
        val initialIsScrapped: Boolean = false,
        val content: String = "",
        val isHost: Boolean = false,
        val date: String = "",
        val track: Track =
            Track(
                trackId = "",
                songTitle = "",
                coverImg = "",
                artistName = "",
                isrc = "",
            ),
        val writer: Writer =
            Writer(
                userId = 0,
                nickname = "",
                profileImg = "",
                isAdmin = false,
            ),
        val like: Like =
            Like(
                isLiked = false,
                count = 0,
            ),
        val initialIsLiked: Boolean = false,
        val badge: BADGE? = null,
        val bottomSheetVisible: Boolean = false,
        val streamingTrackId: String? = null,
    ) : BaseContract.State {
        val homeRefreshRequired: Boolean
            get() = isScrapped != initialIsScrapped || like.isLiked != initialIsLiked
    }

    sealed interface DetailIntent : BaseContract.Intent {
        data class LoadData(
            val postId: Long,
            val badge: BADGE? = null,
        ) : DetailIntent

        data object OnBookmarkClick : DetailIntent

        data object OnStreamClick : DetailIntent

        data object OnLikeClick : DetailIntent

        data object OnMeatBallsClick : DetailIntent

        data object OnWriterProfileClick : DetailIntent

        data object OnBackButtonClick : DetailIntent

        data class OnReportClick(
            val reasons: List<String>,
        ) : DetailIntent

        data object OnDeleteClick : DetailIntent

        data object OnDeleteConfirmClick : DetailIntent

        data class ChangeBottomSheetVisible(
            val visible: Boolean,
        ) : DetailIntent
    }

    sealed interface DetailSideEffect : BaseContract.SideEffect {
        data object ShowDeleteConfirmModal : DetailSideEffect

        data object NavigateBackStack : DetailSideEffect

        data class NavigateToWriterProfile(
            val writerUserId: Long,
        ) : DetailSideEffect

        data class ShowSnackBar(
            val snackBarType: SnackBarType,
            val action: (() -> Unit)? = null,
        ) : DetailSideEffect

        data object NavigateToMyPage : DetailSideEffect
    }
}
