package com.example.data.mapper.todomain

import com.example.data.model.response.BadgesResponse
import com.example.data.model.response.TodayPostItemResponse
import com.example.data.model.response.TodayPostTrackResponse
import com.example.data.model.response.TodayPostsResponse
import com.example.domain.model.Badges
import com.example.domain.model.DailyQuestion
import com.example.domain.model.FeedItem
import com.example.domain.model.HomeScreenData
import com.example.domain.model.Track

fun TodayPostsResponse.toDomain(): HomeScreenData =
    HomeScreenData(
        todayQuestion =
            DailyQuestion(
                questionId = questionId,
                title = title,
                date = date,
            ),
        hasPosted = hasPosted,
        locked = locked,
        totalCount = totalCount,
        todayPosts = items.map { it.toDomain() },
    )

fun TodayPostItemResponse.toDomain(): FeedItem =
    FeedItem(
        postId = postId,
        isScrapped = isScrapped,
        content = content,
        badges = badges.toDomain(),
        track = track.toDomain(),
        writer = user.toDomain(),
        like = like.toDomain(),
    )

private fun BadgesResponse.toDomain(): Badges =
    Badges(
        isEditorPick = isEditorPick,
        isPopular = isPopular,
        isNew = isNew,
    )

private fun TodayPostTrackResponse.toDomain(): Track =
    Track(
        trackId = trackId,
        songTitle = songTitle,
        coverImg = coverImg,
        artistName = artistName,
    )
