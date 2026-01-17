package com.example.detail

import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.domain.model.Like
import com.example.domain.model.Track
import com.example.domain.model.Writer
import com.example.ui.base.BaseContract

class DetailContract {
    data class DetailState(
        val postId: Long = 0L,
        val isScrapped: Boolean = false,
        val content: String = "",
        val isHost: Boolean = false,
        val track: Track =
            Track(
                trackId = "",
                songTitle = "",
                coverImg = "",
                artistName = "",
            ),
        val writer: Writer =
            Writer(
                userId = 0,
                nickname = "",
                profileImg = "",
            ),
        val like: Like =
            Like(
                isLiked = false,
                count = 0,
            ),
        val date: String = "2025-10-19",
        val bottomSheetVisible: Boolean = false,
    ) : BaseContract.State

    sealed interface DetailIntent : BaseContract.Intent {
        data class LoadData(
            val postId: Long,
            val date: String = "",
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

        data class ChangeBottomSheetVisible(
            val visible: Boolean,
        ) : DetailIntent
    }

    sealed interface DetailSideEffect : BaseContract.SideEffect {
        data object NavigateBackStack : DetailSideEffect

        data object ShowBottomSheet : DetailSideEffect

        data object NavigateToWriterProfile : DetailSideEffect

        data class ShowSnackBar(
            val snackBarType: SnackBarType,
            val action: (() -> Unit)? = null,
        ) : DetailSideEffect

        data object NavigateToMyPage : DetailSideEffect
    }
}
