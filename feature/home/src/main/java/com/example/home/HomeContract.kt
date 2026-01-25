package com.example.home

import com.example.designsystem.component.snackbar.type.SnackBarType
import com.example.domain.model.BADGE
import com.example.domain.model.DailyQuestion
import com.example.domain.model.FeedItem
import com.example.ui.base.BaseContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class HomeContract {
    data class HomeState(
        val isLoading: Boolean = true,
        val todayQuestion: DailyQuestion =
            DailyQuestion(
                questionId = 12345,
                title = "여행 갈 때 플레이리스트에 꼭 넣는 노래는?",
                date = "2025-10-19",
            ),
        val hasPosted: Boolean = false,
        val locked: Boolean = true,
        val totalCount: Int = 257,
        val feedItems: ImmutableList<FeedItem> = persistentListOf(),
        val streamingTrackId: String? = null,
    ) : BaseContract.State

    sealed interface HomeIntent : BaseContract.Intent {
        data object OnRefreshClick : HomeIntent

        data class OnBookmarkClick(
            val postId: Long,
        ) : HomeIntent

        data class OnStreamClick(
            val trackId: String,
        ) : HomeIntent

        data class OnLikeClick(
            val postId: Long,
        ) : HomeIntent

        data object OnListClick : HomeIntent

        data class OnWriterProfileClick(
            val writerUserId: Long,
        ) : HomeIntent

        data class OnCoverClick(
            val postId: Long,
        ) : HomeIntent
    }

    sealed interface HomeSideEffect : BaseContract.SideEffect {
        data class NavigateToWriterProfile(
            val writerUserId: Long,
        ) : HomeSideEffect

        data class NavigateToPostDetail(
            val postId: Long,
            val badge: BADGE?,
        ) : HomeSideEffect

        data object NavigateToRecord : HomeSideEffect

        data class ShowSnackBar(
            val snackBarType: SnackBarType,
            val action: (() -> Unit)? = null,
        ) : HomeSideEffect

        data object NavigateToMyPage : HomeSideEffect
    }
}
