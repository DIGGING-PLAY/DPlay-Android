package com.example.mypage

import com.example.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor() : BaseViewModel<MyPageContract.MyPageState, MyPageContract.MyPageIntent, MyPageContract.MyPageSideEffect>(
            MyPageContract.MyPageState(),
        ) {
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
    }
