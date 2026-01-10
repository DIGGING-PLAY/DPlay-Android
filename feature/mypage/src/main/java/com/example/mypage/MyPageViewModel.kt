package com.example.mypage

import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : BaseViewModel<MyPageContract.MyPageState, MyPageContract.MyPageIntent, MyPageContract.MyPageSideEffect>(
            MyPageContract.MyPageState(),
        ) {

        init {
            initializeUserInfo()
        }

        override fun handleIntent(intent: MyPageContract.MyPageIntent) {
            when (intent) {
                MyPageContract.MyPageIntent.OnBottomSheetCancelClick -> {
                }

                MyPageContract.MyPageIntent.OnBottomSheetDeleteClick -> {
                }

                MyPageContract.MyPageIntent.OnDialogCancelClick -> {
                }

                MyPageContract.MyPageIntent.OnDialogDeleteClick -> {
                    // 삭제 api
                }

                is MyPageContract.MyPageIntent.OnKebabIconClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.ShowDeleteBottomSheet)
                }

                is MyPageContract.MyPageIntent.OnMusicItemClick -> {
                    setSideEffect(MyPageContract.MyPageSideEffect.NavigateToDetail(intent.musicId))
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
