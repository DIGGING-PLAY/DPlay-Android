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
            myId == userId -> UserRelation.ME
            else -> UserRelation.OTHER
        }
    }
}