package com.example.otherprofile

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetRegisteredTracksUseCase
import com.example.domain.usecase.GetScrappedTracksUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.model.RegisteredTrackState
import com.example.ui.model.ScrappedTrackState
import com.example.ui.model.toUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = OtherProfileViewModel.Factory::class)
class OtherProfileViewModel
    @AssistedInject
    constructor(
        private val userRepository: UserRepository,
        private val getMyRegisteredTracksUseCase: GetRegisteredTracksUseCase,
        private val getMyScrappedTracksUseCase: GetScrappedTracksUseCase,
        @Assisted private val userId: Long,
    ) : BaseViewModel<OtherProfileContract.OtherProfileState, OtherProfileContract.OtherProfileIntent, OtherProfileContract.OtherProfileSideEffect>(
            OtherProfileContract.OtherProfileState(),
        ) {

        @AssistedFactory
        interface Factory {
            fun create(userId: Long): OtherProfileViewModel
        }

        init {
            initializeUserInfo()
        }

        val registeredTracks: Flow<PagingData<RegisteredTrackState>> =
            getMyRegisteredTracksUseCase(
                userId = userId,
                onTotalCountFetched = {
                    updateState {
                        copy(registeredMusicCount = it)
                    }
                },
            ).map { pagingData ->
                pagingData.map { registeredTrack ->
                    registeredTrack.toUiState()
                }
            }.cachedIn(viewModelScope)

        val scrappedTracks: Flow<PagingData<ScrappedTrackState>> =
            getMyScrappedTracksUseCase(
                userId = userId,
            ).map { pagingData ->
                pagingData.map { scrappedTrack ->
                    scrappedTrack.toUiState()
                }
            }.cachedIn(viewModelScope)

        override fun handleIntent(intent: OtherProfileContract.OtherProfileIntent) {
            when (intent) {
                OtherProfileContract.OtherProfileIntent.OnBackIconClick -> {
                    setSideEffect(OtherProfileContract.OtherProfileSideEffect.NavigateToBack)
                }

                is OtherProfileContract.OtherProfileIntent.OnRegisteredTrackClick -> {
                    setSideEffect(OtherProfileContract.OtherProfileSideEffect.NavigateToDetail(intent.postId))
                }

                is OtherProfileContract.OtherProfileIntent.OnScrappedTrackClick -> {
                    setSideEffect(OtherProfileContract.OtherProfileSideEffect.NavigateToDetail(intent.postId))
                }

                is OtherProfileContract.OtherProfileIntent.OnTabClick -> {
                    updateState {
                        copy(selectedTabIndex = intent.tabIndex)
                    }
                }
            }
        }

        private fun initializeUserInfo() {
            viewModelScope.launch {
                userRepository
                    .getUser(userId)
                    .onSuccess { user ->
                        updateState{
                            copy(
                                userNickname = user.nickname,
                                profileImagePath = user.profileImagePath,
                            )
                        }
                    }.onFailure {

                    }
            }
        }
    }
