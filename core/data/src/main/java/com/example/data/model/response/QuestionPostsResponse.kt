package com.example.data.model.response

import com.example.domain.model.Badges
import com.example.domain.model.FeedItem
import com.example.domain.model.Like
import com.example.domain.model.Track
import com.example.domain.model.Writer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionPostsResponse(
    @SerialName("questionId")
    val questionId: Long,
    @SerialName("date")
    val date: String,
    @SerialName("title")
    val title: String,
    @SerialName("hasPosted")
    val hasPosted: Boolean,
    @SerialName("locked")
    val locked: Boolean,
    @SerialName("visibleLimit")
    val visibleLimit: Int,
    @SerialName("totalCount")
    val totalCount: Int,
    @SerialName("nextCursor")
    val nextCursor: String?,
    @SerialName("items")
    val items: List<QuestionPostItemResponse>,
)

@Serializable
data class QuestionPostItemResponse(
    @SerialName("postId")
    val postId: Long,
    @SerialName("isEditorPick")
    val isEditorPick: Boolean,
    @SerialName("isScrapped")
    val isScrapped: Boolean,
    @SerialName("content")
    val content: String,
    @SerialName("track")
    val track: TodayPostTrackResponse,
    @SerialName("user")
    val user: UserResponse,
    @SerialName("like")
    val like: LikeResponse,
) {
    fun toDomain(): FeedItem =
        FeedItem(
            postId = postId,
            isScrapped = isScrapped,
            content = content,
            badges = Badges(
                isEditorPick = isEditorPick,
                isPopular = false,
                isNew = false,
            ),
            track = Track(
                trackId = track.trackId,
                songTitle = track.songTitle,
                coverImg = track.coverImg,
                artistName = track.artistName,
                isrc = "",
            ),
            writer = Writer(
                userId = user.userId,
                nickname = user.nickname,
                profileImg = user.profileImg.orEmpty(),
            ),
            like = Like(
                isLiked = like.isLiked,
                count = like.count,
            ),
        )
}
