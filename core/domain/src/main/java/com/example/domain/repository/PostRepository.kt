package com.example.domain.repository

import com.example.domain.model.Track

interface PostRepository {
    suspend fun registerPost(
        track: Track,
        comment: String,
    ): Result<Unit>
}