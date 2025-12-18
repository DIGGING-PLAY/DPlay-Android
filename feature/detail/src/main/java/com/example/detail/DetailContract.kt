package com.example.detail

import com.example.common.model.Badges
import com.example.common.model.FeedItem
import com.example.common.model.Like
import com.example.common.model.Track
import com.example.common.model.Writer
import com.example.ui.base.BaseContract

class DetailContract {
    data class DetailState(
        val data: FeedItem =
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
                        artistName = "Artist1,Artist2",
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
        val date: String = "2025-10-19",
    ) : BaseContract.State

    sealed interface DetailIntent : BaseContract.Intent {
        data object Initialize : DetailIntent

        data class OnClickNumberButton(
            val number: Int,
        ) : DetailIntent
    }

    sealed interface DetailSideEffect : BaseContract.SideEffect {
        data class ShowSnackBar(
            val message: String,
        ) : DetailSideEffect
    }
}
