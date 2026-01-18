package com.example.mypage

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetMyRegisteredTracksUseCase
import com.example.domain.usecase.GetMyScrappedTracksUseCase
import com.example.ui.base.BaseViewModel
import com.example.ui.model.RegisteredTrackState
import com.example.ui.model.ScrappedTrackState
import com.example.ui.model.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
        private val getMyRegisteredTracksUseCase: GetMyRegisteredTracksUseCase,
        private val getMyScrappedTracksUseCase: GetMyScrappedTracksUseCase,
    ) : BaseViewModel<MyPageContract.MyPageState, MyPageContract.MyPageIntent, MyPageContract.MyPageSideEffect>(
            MyPageContract.MyPageState(),
        ) {
        init {
            initializeUserInfo()
        }

        val registeredTracks: Flow<PagingData<RegisteredTrackState>> =
            getMyRegisteredTracksUseCase(
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
            .combine(uiState){ pagingData, state ->
                pagingData.filter { !state.deletedTrackIds.contains(it.postId) }
            }

        val scrappedTracks: Flow<PagingData<ScrappedTrackState>> =
            getMyScrappedTracksUseCase()
                .map { pagingData ->
                    pagingData.map { scrappedTrack ->
                        scrappedTrack.toUiState()
                    }
                }.cachedIn(viewModelScope)

        override fun handleIntent(intent: MyPageContract.MyPageIntent) {
            when (intent) {
                MyPageContract.MyPageIntent.OnBottomSheetCancelClick -> {
                    updateState{
                        copy(
                            isDeleteBottomSheetVisible = false,
                        )
                    }
                    setSideEffect(MyPageContract.MyPageSideEffect.ShowBottomNavigation)
                }

                MyPageContract.MyPageIntent.OnBottomSheetDeleteClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.ShowBottomNavigation)
                    updateState{
                        copy(
                            isDeleteBottomSheetVisible = false,
                        )
                    }
                    setSideEffect(MyPageContract.MyPageSideEffect.ShowDeleteDialogue)
                }

                is MyPageContract.MyPageIntent.OnRegisteredTrackClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.NavigateToDetail(intent.postId))
                }

                MyPageContract.MyPageIntent.OnDialogDeleteClick -> {
                    viewModelScope.launch {
                        val selectedPostId = currentState.selectedPostId
                        postRepository.deletePost(selectedPostId)
                            .onSuccess {
                                updateState {
                                    copy(
                                        deletedTrackIds = deletedTrackIds.add(selectedPostId)
                                    )
                                }
                            }.onFailure {
                                Timber.d(t = it, message = "deletePost 실패")
                            }
                    }
                }

                is MyPageContract.MyPageIntent.OnKebabIconClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.HideBottomNavigation)
                    updateState{
                        copy(
                            isDeleteBottomSheetVisible = true,
                            selectedPostId = intent.musicId
                        )
                    }
                }

                is MyPageContract.MyPageIntent.OnScrappedTrackClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.NavigateToDetail(intent.postId))
                }

                MyPageContract.MyPageIntent.OnProfileClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.NavigateToEditProfile)
                }

                MyPageContract.MyPageIntent.OnSettingIconClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.NavigateToSettings)
                }

                is MyPageContract.MyPageIntent.OnTabClick -> {
                    updateState {
                        copy(selectedTabIndex = intent.tabIndex)
                    }
                }
            }
        }

        private fun initializeUserInfo() {
            userRepository
                .getUser()
                .onEach { user ->
                    updateState {
                        copy(
                            userNickname = user?.nickname ?: "",
                            profileImagePath = user?.profileImagePath,
                        )
                    }
                    Timber.d("user: $user")
                }.launchIn(viewModelScope)
        }
    }
