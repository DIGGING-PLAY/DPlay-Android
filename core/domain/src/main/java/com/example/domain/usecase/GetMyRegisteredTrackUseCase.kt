package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.RegisteredTrack
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMyRegisteredTracksUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        onTotalCountFetched: (Int) -> Unit
    ): Flow<PagingData<RegisteredTrack>> {
        return userRepository.getUser()
            .flatMapLatest { user ->
                if (user == null) {
                    flowOf(PagingData.empty())
                } else {
                    userRepository.getRegisteredTracks(userId = user.id, onTotalCountFetched = onTotalCountFetched)
                }
            }
    }
}