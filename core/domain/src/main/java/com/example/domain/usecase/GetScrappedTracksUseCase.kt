package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.ScrappedTrack
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetScrappedTracksUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        userId: Long? = null
    ): Flow<PagingData<ScrappedTrack>> {
        return userId?.let{
            userRepository.getScrappedTracks(userId = userId)
        } ?: userRepository.getUser()
                .flatMapLatest { user ->
                    if (user == null) {
                        flowOf(PagingData.empty())
                    } else {
                        userRepository.getScrappedTracks(userId = user.id)
                    }
                }
    }
}