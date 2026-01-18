package com.example.data.mapper.todomain

import com.example.data.model.response.LikeResponse
import com.example.domain.model.Like

fun LikeResponse.toDomain(): Like =
    Like(
        isLiked = this.isLiked,
        count = this.count,
    )
