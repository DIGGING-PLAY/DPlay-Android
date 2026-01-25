package com.example.mypage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayButtonBottomSheet
import com.example.designsystem.component.DPlayMusicGridItem
import com.example.designsystem.component.DPlayMusicListItem
import com.example.designsystem.component.DPlayProfileImageArea
import com.example.designsystem.component.DplayRightIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayCircleButton
import com.example.designsystem.component.button.type.CircleButtonType
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import com.example.navigation.Detail
import com.example.navigation.EditProfile
import com.example.navigation.MyPageTab
import com.example.navigation.Navigator
import com.example.navigation.Setting
import com.example.ui.controller.LocalBottomNavigationController
import com.example.ui.controller.LocalModalController
import com.example.ui.emptyLazyPagingItems
import com.example.ui.model.RegisteredTrackState
import com.example.ui.model.ScrappedTrackState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageRoute(
    navigator: Navigator,
    initialTab: MyPageTab = MyPageTab.REGISTERED,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(initialTab) {
        val tabIndex =
            when (initialTab) {
                MyPageTab.REGISTERED -> 0
                MyPageTab.BOOKMARKED -> 1
            }
        viewModel.handleIntent(MyPageContract.MyPageIntent.OnTabClick(tabIndex))
    }

    val registeredTracks = viewModel.registeredTracks.collectAsLazyPagingItems()
    val scrappedTracks = viewModel.scrappedTracks.collectAsLazyPagingItems()

    val context = LocalContext.current
    val bottomNavigationController = LocalBottomNavigationController.current
    val modalController = LocalModalController.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                is MyPageContract.MyPageSideEffect.NavigateToDetail -> {
                    navigator.navigateTo(destination = Detail(postId = sideEffect.postId))
                }
                MyPageContract.MyPageSideEffect.NavigateToEditProfile -> {
                    navigator.navigateTo(destination = EditProfile)
                }
                MyPageContract.MyPageSideEffect.NavigateToSettings -> {
                    navigator.navigateTo(destination = Setting)
                }
                MyPageContract.MyPageSideEffect.HideBottomNavigation -> {
                    bottomNavigationController.hide()
                }
                MyPageContract.MyPageSideEffect.ShowBottomNavigation -> {
                    bottomNavigationController.show()
                }
                MyPageContract.MyPageSideEffect.ShowDeleteDialogue -> {
                    modalController.showWarningModal(
                        mainText = "정말 삭제하시겠어요?",
                        subText = null,
                        onLeftButtonClick = { modalController.hideModal() },
                        onRightButtonClick = {
                            modalController.hideModal()
                            viewModel.handleIntent(MyPageContract.MyPageIntent.OnDialogDeleteClick)
                        },
                        onDismiss = { modalController.hideModal() },
                        leftButtonLabel = "취소",
                        rightButtonLabel = "삭제하기",
                    )
                }
            }
        }
    }

    MyPageScreen(
        state = state,
        registeredTrackList = registeredTracks,
        scrappedTrackList = scrappedTracks,
        modifier = modifier,
        onTabSelected = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnTabClick(it))
        },
        onSettingIconClick = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnSettingIconClick)
        },
        onProfileImageClick = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnProfileClick)
        },
        onScrappedTrackClick = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnScrappedTrackClick(it))
        },
        onKebabIconClick = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnKebabIconClick(it))
        },
        onBottomSheetCancelClick = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnBottomSheetCancelClick)
        },
        onBottomSheetDeleteClick = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnBottomSheetDeleteClick)
        },
        onRegisteredTrackClick = {
            viewModel.handleIntent(MyPageContract.MyPageIntent.OnRegisteredTrackClick(it))
        },
    )
}

@Composable
fun MyPageScreen(
    state: MyPageContract.MyPageState,
    registeredTrackList: LazyPagingItems<RegisteredTrackState>,
    scrappedTrackList: LazyPagingItems<ScrappedTrackState>,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit = {},
    onSettingIconClick: () -> Unit = {},
    onProfileImageClick: () -> Unit = {},
    onScrappedTrackClick: (Long) -> Unit = {},
    onKebabIconClick: (Long) -> Unit = {},
    onBottomSheetCancelClick: () -> Unit = {},
    onBottomSheetDeleteClick: () -> Unit = {},
    onRegisteredTrackClick: (Long) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(DPlayTheme.colors.dplayWhite),
        ) {
            DplayRightIconTitleTopAppBar(
                title = stringResource(com.dplay.mypage.R.string.mypage_screen_title),
            ) {
                onSettingIconClick()
            }

            Spacer(modifier = Modifier.height(12.dp))

            UserInformationRow(
                nickname = state.userNickname,
                registeredMusicCount = state.registeredMusicCount,
                profileImagePath = state.profileImagePath,
                onProfileImageClick = { onProfileImageClick() },
            )

            Spacer(modifier = Modifier.height(20.dp))

            TabContent(
                selectedTabIndex = state.selectedTabIndex,
                onTabSelected = onTabSelected,
                registeredTrackList = registeredTrackList,
                scrappedTrackList = scrappedTrackList,
                onScrappedTrackClick = onScrappedTrackClick,
                onKebabIconClick = onKebabIconClick,
                onRegisteredTrackClick = onRegisteredTrackClick,
            )
        }
        if (state.isDeleteBottomSheetVisible) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color = DPlayTheme.colors.dim40)
                        .noRippleClickable { onBottomSheetCancelClick() },
            )
        }

        AnimatedVisibility(
            visible = state.isDeleteBottomSheetVisible,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter =
                slideInVertically(
                    initialOffsetY = { it },
                ),
            exit =
                slideOutVertically(
                    targetOffsetY = { 0 },
                ),
        ) {
            DPlayButtonBottomSheet(
                mainText = "삭제하기",
                subText = "취소하기",
                mainOnClick = { onBottomSheetDeleteClick() },
                subOnClick = { onBottomSheetCancelClick() },
                modifier = Modifier.noRippleClickable(),
                mainButtonColor = DPlayTheme.colors.alertRed,
            )
        }
    }
}

@Composable
private fun UserInformationRow(
    nickname: String,
    registeredMusicCount: Int,
    profileImagePath: String?,
    onProfileImageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.5.dp),
        ) {
            Text(
                text = nickname,
                style = DPlayTheme.typography.titleBold24,
                color = DPlayTheme.colors.dplayBlack,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text =
                    buildAnnotatedString {
                        val rawText = stringResource(id = com.dplay.mypage.R.string.registered_music_count)
                        val parts = rawText.split($$"""%1$s""")

                        append(parts[0])
                        withStyle(style = SpanStyle(color = DPlayTheme.colors.dplayPink)) {
                            append("$registeredMusicCount")
                        }
                        append(parts[1])
                    },
                style = DPlayTheme.typography.bodySemi14,
                color = DPlayTheme.colors.gray400,
            )
        }

        DPlayProfileImageArea(
            onProfileImageClick = onProfileImageClick,
            profileImagePath = profileImagePath,
            modifier = Modifier.size(80.dp),
        ) {
            DPlayCircleButton(
                circleButtonType =
                    CircleButtonType.SmallEdit(
                        R.string.edit_profile_image_button_icon_description,
                    ),
                onClick = { onProfileImageClick() },
            )
        }
    }
}

@Composable
private fun TabContent(
    selectedTabIndex: Int,
    registeredTrackList: LazyPagingItems<RegisteredTrackState>,
    scrappedTrackList: LazyPagingItems<ScrappedTrackState>,
    onTabSelected: (Int) -> Unit,
    onScrappedTrackClick: (Long) -> Unit = {},
    onKebabIconClick: (Long) -> Unit = {},
    onRegisteredTrackClick: (Long) -> Unit = {},
) {
    Column {
        MyPageTabRow(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
        )

        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = DPlayTheme.colors.gray100),
        ) {
            when (selectedTabIndex) {
                0 ->
                    RegisteredMusicList(
                        registeredTrackList = registeredTrackList,
                        onKebabIconClick = onKebabIconClick,
                        onRegisteredTrackClick = onRegisteredTrackClick,
                    )
                1 ->
                    BookmarkedMusicList(
                        scrappedTrackList = scrappedTrackList,
                        onScrappedTrackClick = onScrappedTrackClick,
                    )
            }
        }
    }
}

@Composable
private fun MyPageTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit = {},
) {
    val tabs =
        listOf(
            stringResource(com.dplay.mypage.R.string.registered_music_tab_label),
            stringResource(com.dplay.mypage.R.string.bookmarked_music_tab_label),
        )
    val indicatorHorizontalPadding = 28.dp

    val density = LocalDensity.current
    val textWidths =
        remember {
            mutableStateListOf<Dp>().apply { repeat(tabs.size) { add(0.dp) } }
        }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val totalWidth = maxWidth
        val tabWidth = totalWidth / tabs.size

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                tabs.forEachIndexed { index, title ->
                    Box(
                        modifier =
                            Modifier
                                .weight(1f)
                                .noRippleClickable {
                                    onTabSelected(index)
                                }.padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = title,
                            style = DPlayTheme.typography.bodyBold16,
                            color = if (selectedTabIndex == index) DPlayTheme.colors.dplayBlack else DPlayTheme.colors.gray300,
                            onTextLayout = { textLayoutResult ->
                                textWidths[index] = with(density) { textLayoutResult.size.width.toDp() }
                            },
                        )
                    }
                }
            }

            // 인디케이터 영역
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(2.dp),
            ) {
                val currentTextWidth = textWidths[selectedTabIndex]

                val targetIndicatorWidth = currentTextWidth + (indicatorHorizontalPadding * 2)
                val targetIndicatorOffset = (tabWidth * selectedTabIndex) + ((tabWidth - targetIndicatorWidth) / 2)

                val indicatorOffset by animateDpAsState(targetValue = targetIndicatorOffset, label = "offset")
                val indicatorWidth by animateDpAsState(targetValue = targetIndicatorWidth, label = "width")

                // 인디케이터
                Box(
                    modifier =
                        Modifier
                            .offset(x = indicatorOffset)
                            .width(indicatorWidth)
                            .height(2.dp)
                            .background(color = DPlayTheme.colors.dplayBlack),
                )

                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = DPlayTheme.colors.gray200)
                            .align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
private fun RegisteredMusicList(
    registeredTrackList: LazyPagingItems<RegisteredTrackState>,
    modifier: Modifier = Modifier,
    onKebabIconClick: (Long) -> Unit = {},
    onRegisteredTrackClick: (Long) -> Unit = {},
) {
    if (registeredTrackList.itemCount == 0) {
        RegisteredMusicEmptyView()
    } else {
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = registeredTrackList.itemCount,
                key = registeredTrackList.itemKey { it.postId },
            ) { index ->
                val registeredTrack = registeredTrackList[index]

                if (registeredTrack != null) {
                    DPlayMusicListItem(
                        musicImageUrl = registeredTrack.track.thumbnailUrl,
                        musicName = registeredTrack.track.musicTitle,
                        musicArtistName = registeredTrack.track.artistName,
                        musicContent = registeredTrack.comment,
                        onMoreClick = { onKebabIconClick(registeredTrack.postId) },
                        onClick = { onRegisteredTrackClick(registeredTrack.postId) },
                    )
                }
            }
        }
    }
}

@Composable
private fun BookmarkedMusicList(
    scrappedTrackList: LazyPagingItems<ScrappedTrackState>,
    modifier: Modifier = Modifier,
    onScrappedTrackClick: (Long) -> Unit = {},
) {
    if (scrappedTrackList.itemCount == 0) {
        ScrappedMusicEmptyView()
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                count = scrappedTrackList.itemCount,
                key = scrappedTrackList.itemKey { it.postId },
            ) { index ->
                val scrappedTrack = scrappedTrackList[index]

                if (scrappedTrack != null) {
                    DPlayMusicGridItem(
                        musicImageUrl = scrappedTrack.track.thumbnailUrl,
                        musicName = scrappedTrack.track.musicTitle,
                        musicArtistName = scrappedTrack.track.artistName,
                        onClick = { onScrappedTrackClick(scrappedTrack.postId) },
                    )
                } else {
                }
            }
        }
    }
}

@Composable
private fun RegisteredMusicEmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(184.dp))

        Text(
            text = "아직 등록한 곡이 없어요",
            style = DPlayTheme.typography.bodySemi14,
            color = DPlayTheme.colors.gray400,
        )
    }
}

@Composable
private fun ScrappedMusicEmptyView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(184.dp))

        Text(
            text = "아직 저장한 곡이 없어요",
            style = DPlayTheme.typography.bodySemi14,
            color = DPlayTheme.colors.gray400,
        )
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    var uiState by remember {
        mutableStateOf(
            MyPageContract.MyPageState(
                userNickname = "디플레이",
            ),
        )
    }

    DPlayTheme {
        MyPageScreen(
            state = uiState,
            registeredTrackList = emptyLazyPagingItems(),
            scrappedTrackList = emptyLazyPagingItems(),
            onTabSelected = {
                uiState = uiState.copy(selectedTabIndex = it)
            },
        )
    }
}
