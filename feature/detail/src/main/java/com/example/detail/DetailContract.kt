package com.example.detail

import com.example.common.model.Badges
import com.example.common.model.FeedItem
import com.example.common.model.Like
import com.example.common.model.Track
import com.example.common.model.Writer
import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.ui.base.BaseContract

class DetailContract {
    data class DetailState(
        val data: FeedItem =
            FeedItem(
                postId = 111,
                isScrapped = false,
                content = "",
                badges =
                    Badges(
                        isEditorPick = false,
                        isPopular = false,
                        isNew = false,
                    ),
                track =
                    Track(
                        trackId = "",
                        songTitle = "",
                        coverImg = "",
                        artistName = "",
                    ),
                writer =
                    Writer(
                        userId = 0,
                        nickname = "",
                        profileImg = "",
                    ),
                like =
                    Like(
                        isLiked = false,
                        count = 0,
                    ),
            ),
        val date: String = "2025-10-19",
    ) : BaseContract.State

    sealed interface DetailIntent : BaseContract.Intent {
        data class LoadData(
            val postId: Long,
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
