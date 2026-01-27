package com.example.domain.usecase

import com.example.domain.model.UserRelation
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckUserRelationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Long): UserRelation {
        val myId = userRepository.getUser().first()?.id
        return when {
            userId == ADMIN_ID -> UserRelation.ADMIN
            myId == userId -> UserRelation.ME
            else -> UserRelation.OTHER
        }
    }

    companion object{
        const val ADMIN_ID = 1L
    }
}