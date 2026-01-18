package com.example.data.mapper.todomain

import com.example.data.model.response.PostDetailResponse
import com.example.data.model.response.TrackResponse
import com.example.domain.model.PostDetail
import com.example.domain.model.Track

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
        isrc = "",
    )
