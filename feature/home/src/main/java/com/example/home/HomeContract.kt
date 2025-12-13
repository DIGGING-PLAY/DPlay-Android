package com.example.home

import com.dplay.common.model.Badges
import com.dplay.common.model.FeedItem
import com.dplay.common.model.Like
import com.dplay.common.model.TodayQuestion
import com.dplay.common.model.Track
import com.dplay.common.model.Writer
import com.example.ui.base.BaseContract

val dummyFeedItems =
    listOf(
        FeedItem(
            postId = 111,
            isScrapped = true,
            content = "그냥 좋아요 이 노래",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = true,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:203948",
                    songTitle = "Song Title 1",
                    coverImg = "https://picsum.photos/300",
                    artistNames = listOf("Artist1", "Artist2"),
                ),
            writer =
                Writer(
                    userId = 222,
                    nickname = "윤서암",
                    profileImg = "https://picsum.photos/200",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 24,
                ),
        ),
        FeedItem(
            postId = 112,
            isScrapped = false,
            content = "비 오는 날 꼭 듣는 노래에요",
            badges =
                Badges(
                    isEditorPick = true,
                    isPopular = false,
                    isNew = false,
                ),
            track =
                Track(
                    trackId = "apple:204837",
                    songTitle = "Song Title 2",
                    coverImg = "https://picsum.photos/310",
                    artistNames = listOf("Artist3"),
                ),
            writer =
                Writer(
                    userId = 333,
                    nickname = "민석",
                    profileImg = "https://picsum.photos/201",
                ),
            like =
                Like(
                    isLiked = true,
                    count = 57,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistNames = listOf("Artist4"),
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistNames = listOf("Artist4"),
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistNames = listOf("Artist4"),
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
        FeedItem(
            postId = 113,
            isScrapped = false,
            content = "출근길에 항상 듣습니다!",
            badges =
                Badges(
                    isEditorPick = false,
                    isPopular = false,
                    isNew = true,
                ),
            track =
                Track(
                    trackId = "apple:204111",
                    songTitle = "Song Title 3",
                    coverImg = "https://picsum.photos/320",
                    artistNames = listOf("Artist4"),
                ),
            writer =
                Writer(
                    userId = 444,
                    nickname = "서현",
                    profileImg = "https://picsum.photos/202",
                ),
            like =
                Like(
                    isLiked = false,
                    count = 13,
                ),
        ),
    )

class HomeContract {
    data class HomeState(
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
        val feedItems: List<FeedItem> = dummyFeedItems,
    ) : BaseContract.State

    sealed interface HomeIntent : BaseContract.Intent {
        data object Initialize : HomeIntent

        data class OnClickNumberButton(
            val number: Int,
        ) : HomeIntent
    }

    sealed interface HomeSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : HomeSideEffect
    }
}
