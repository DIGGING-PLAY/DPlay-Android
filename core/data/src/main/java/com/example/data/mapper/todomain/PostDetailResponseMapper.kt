package com.example.data.mapper.todomain

import com.example.data.model.response.LikeResponse
import com.example.data.model.response.PostDetailResponse
import com.example.data.model.response.TrackResponse
import com.example.data.model.response.UserResponse
import com.example.domain.model.Like
import com.example.domain.model.PostDetail
import com.example.domain.model.Track
import com.example.domain.model.Writer

fun PostDetailResponse.toDomain(): PostDetail =
    PostDetail(
        postId = this.postId,
        isHost = this.isHost,
        isScrapped = this.isScrapped,
        content = this.content,
        track = this.track.toDomain(),
        writer = this.user.toDomain(),
        like = this.like.toDomain(),
    )

private fun TrackResponse.toDomain(): Track =
    Track(
        trackId = this.trackId,
        songTitle = this.songTitle,
        coverImg = this.coverImg,
        artistName = this.artistName,
    )

private fun UserResponse.toDomain(): Writer =
    Writer(
        userId = this.userId,
        nickname = this.nickname,
        profileImg = this.profileImg ?: "",
    )

private fun LikeResponse.toDomain(): Like =
    Like(
        isLiked = this.isLiked,
        count = this.count,
    )
