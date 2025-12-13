package com.example.home

import com.example.common.model.FeedItem
import com.example.common.model.TodayQuestion
import com.example.ui.base.BaseContract

class HomeContract {
    data class HomeState(
        val isLoading: Boolean = true,
        val todayQuestion: TodayQuestion =
            TodayQuestion(
                questionId = 12345,
                title = "여행 갈 때 플레이리스트에 꼭 넣는 노래는?",
                date = "2025-10-19",
            ),
        val questionId: Long = 12345,
        val date: String = "2025-10-19",
        val hasPosted: Boolean = false,
        val locked: Boolean = true,
        val totalCount: Int = 257,
        val feedItems: List<FeedItem> = emptyList(),
    ) : BaseContract.State

    sealed interface HomeIntent : BaseContract.Intent {
        data object LoadHomeData : HomeIntent

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
        ) : HomeSideEffect

        data object NavigateToRecommend : HomeSideEffect
    }
}
