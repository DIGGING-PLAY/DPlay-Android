package com.example.record

import com.example.domain.model.Badges
import com.example.domain.model.DailyQuestion
import com.example.domain.model.FeedItem
import com.example.domain.model.Like
import com.example.domain.model.Track
import com.example.domain.model.Writer
import com.example.ui.base.BaseContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class RecordContract {
    data class RecordState(
        val loading: Boolean = false,
        val questionList: ImmutableList<DailyQuestion> = persistentListOf(),
        val year: Int = 2026,
        val month: Int = 1,
        val selectedQuestion: DailyQuestion? = null,
        val datePickerBottomSheetVisible: Boolean = false,
        val recordList: ImmutableList<FeedItem> =
            persistentListOf(
                FeedItem(
                    postId = 1L,
                    isScrapped = false,
                    content = "오늘 듣기 딱 좋은 노래 추천합니다 🎧",
                    badges =
                        Badges(
                            isEditorPick = true,
                            isPopular = false,
                            isNew = false,
                        ),
                    track =
                        Track(
                            trackId = "track_1",
                            songTitle = "Blue Night",
                            coverImg = "https://picsum.photos/300/300?1",
                            artistName = "IU",
                            isrc = "USUC1234567890",
                        ),
                    writer =
                        Writer(
                            userId = 101L,
                            nickname = "민석",
                            profileImg = "https://picsum.photos/100/100?1",
                        ),
                    like =
                        Like(
                            isLiked = false,
                            count = 123,
                        ),
                ),
                FeedItem(
                    postId = 2L,
                    isScrapped = true,
                    content = "비 오는 날엔 이 곡이 최고 ☔️",
                    badges =
                        Badges(
                            isEditorPick = false,
                            isPopular = true,
                            isNew = false,
                        ),
                    track =
                        Track(
                            trackId = "track_2",
                            songTitle = "Rain Drop",
                            coverImg = "https://picsum.photos/300/300?2",
                            artistName = "DEAN",
                            isrc = "USUC1234567891",
                        ),
                    writer =
                        Writer(
                            userId = 102L,
                            nickname = "재훈",
                            profileImg = "https://picsum.photos/100/100?2",
                        ),
                    like =
                        Like(
                            isLiked = true,
                            count = 842,
                        ),
                ),
                FeedItem(
                    postId = 3L,
                    isScrapped = false,
                    content = "요즘 계속 반복재생 중 🔁",
                    badges =
                        Badges(
                            isEditorPick = false,
                            isPopular = false,
                            isNew = true,
                        ),
                    track =
                        Track(
                            trackId = "track_3",
                            songTitle = "Sunset Drive",
                            coverImg = "https://picsum.photos/300/300?3",
                            artistName = "Lauv",
                            isrc = "USUC1234567892",
                        ),
                    writer =
                        Writer(
                            userId = 103L,
                            nickname = "수진",
                            profileImg = "https://picsum.photos/100/100?3",
                        ),
                    like =
                        Like(
                            isLiked = false,
                            count = 45,
                        ),
                ),
                FeedItem(
                    postId = 4L,
                    isScrapped = false,
                    content = "이건 진짜 숨은 명곡임",
                    badges =
                        Badges(
                            isEditorPick = false,
                            isPopular = false,
                            isNew = false,
                        ),
                    track =
                        Track(
                            trackId = "track_4",
                            songTitle = "Midnight Walk",
                            coverImg = "https://picsum.photos/300/300?4",
                            artistName = "Joji",
                            isrc = "USUC1234567893",
                        ),
                    writer =
                        Writer(
                            userId = 104L,
                            nickname = "하늘",
                            profileImg = "https://picsum.photos/100/100?4",
                        ),
                    like =
                        Like(
                            isLiked = true,
                            count = 9,
                        ),
                ),
            ),
    ) : BaseContract.State

    sealed interface RecordIntent : BaseContract.Intent {
        data object Initialize : RecordIntent

        data class OnQuestionClick(
            val question: DailyQuestion,
        ) : RecordIntent

        data object OnListBackButtonClick : RecordIntent

        data class ChangeBottomSheetVisible(
            val isVisible: Boolean,
        ) : RecordIntent

        data class OnMusicClick(
            val postId: Long,
        ) : RecordIntent

        data class SelectDate(
            val year: Int,
            val month: Int,
        ) : RecordIntent
    }

    sealed interface RecordSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : RecordSideEffect

        data class NavigateToPostDetail(
            val postId: Long,
        ) : RecordSideEffect
    }
}
