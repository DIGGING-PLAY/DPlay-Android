package com.example.data.mapper.todomain

import com.example.data.model.response.PostDetailResponse
import com.example.domain.model.PostDetail

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
