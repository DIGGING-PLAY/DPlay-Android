package com.example.data.mapper.todomain

import com.example.data.model.response.UserResponse
import com.example.domain.model.Writer

fun UserResponse.toDomain(): Writer =
    Writer(
        userId = this.userId,
        nickname = this.nickname,
        profileImg = this.profileImg,
        isAdmin = this.isAdmin,
    )
