package com.example.mypage

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.dplay.designsystem.R
import com.example.designsystem.component.DPlayMusicGridItem
import com.example.designsystem.component.DPlayMusicListItem
import com.example.designsystem.component.DplayRightIconTitleTopAppBar
import com.example.designsystem.component.button.DPlayCircleButton
import com.example.designsystem.component.button.type.CircleButtonType
import com.example.designsystem.theme.DPlayTheme
import com.example.designsystem.util.noRippleClickable
import com.example.navigation.Navigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyPageRoute(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MyPageScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
fun MyPageScreen(
    state: MyPageContract.MyPageState,
    onTabSelected: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DPlayTheme.colors.dplayWhite),
    ) {
        DplayRightIconTitleTopAppBar(
            title = "마이페이지",
        ) {  }

        Spacer(modifier = Modifier.height(12.dp))

        UserInformationRow(
            nickname = state.userNickname,
            registeredMusicCount = state.registeredMusicCount,
            profileImageUri = state.profileImageUri,
            onProfileImageClick = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        TabContent(
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = onTabSelected,
            registeredMusicList = state.registeredMusicList,
            bookmarkedMusicList = state.bookmarkedMusicList
        )
    }
}



@Composable
private fun UserInformationRow(
    nickname: String,
    registeredMusicCount: Int,
    profileImageUri: Uri?,
    onProfileImageClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Column(
            modifier = Modifier.padding(vertical = 12.5.dp)
        ){
            Text(
                text = nickname,
                style = DPlayTheme.typography.titleBold24,
                color = DPlayTheme.colors.dplayBlack
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = buildAnnotatedString {
                    append("총 ")
                    withStyle(style = SpanStyle(color = DPlayTheme.colors.dplayPink)) {
                        append("$registeredMusicCount")
                    }
                    append("개의 노래를 공유했어요")
                },
                style = DPlayTheme.typography.bodySemi14,
                color = DPlayTheme.colors.gray400
            )
        }
        
        Box(
            modifier =
                Modifier
                    .noRippleClickable(
                        onClick = { onProfileImageClick() },
                    ),
        ) {
            AsyncImage(
                model = profileImageUri,
                contentDescription = null,
                modifier =
                    Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = DPlayTheme.colors.gray200,
                            shape = CircleShape,
                        ),
                contentScale = ContentScale.Crop,
            )

            DPlayCircleButton(
                circleButtonType =
                    CircleButtonType.SmallEdit(
                        R.string.edit_profile_image_button_icon_description,
                    ),
                onClick = {},
                modifier = Modifier.align(Alignment.BottomEnd),
            )
        }
    }
}

@Composable
private fun TabContent(
    selectedTabIndex: Int,
    registeredMusicList: ImmutableList<RegisteredMusic>,
    bookmarkedMusicList: ImmutableList<BookmarkedMusic>,
    onTabSelected: (Int) -> Unit,
) {
    Column {
        MyPageTabRow(
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = DPlayTheme.colors.gray100)
        ){
            when (selectedTabIndex) {
                0 -> RegisteredMusicList(
                    registeredMusicList = registeredMusicList
                )
                1 -> BookmarkedMusicList(
                    bookmarkedMusicList = bookmarkedMusicList
                )
            }
        }
    }
}

@Composable
private fun MyPageTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val tabs = listOf("등록한 곡", "보관함")
    val indicatorHorizontalPadding = 28.dp

    val density = LocalDensity.current
    val textWidths = remember {
        mutableStateListOf<Dp>().apply { repeat(tabs.size) { add(0.dp) } }
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val totalWidth = maxWidth
        val tabWidth = totalWidth / tabs.size

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                tabs.forEachIndexed { index, title ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .noRippleClickable{
                                onTabSelected(index)
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            style = DPlayTheme.typography.bodyBold16,
                            color = if (selectedTabIndex == index) DPlayTheme.colors.dplayBlack else DPlayTheme.colors.gray300,
                            onTextLayout = { textLayoutResult ->
                                textWidths[index] = with(density) { textLayoutResult.size.width.toDp() }
                            }
                        )
                    }
                }
            }

            // 인디케이터 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            ) {
                val currentTextWidth = textWidths[selectedTabIndex]

                val targetIndicatorWidth = currentTextWidth + (indicatorHorizontalPadding * 2)
                val targetIndicatorOffset = (tabWidth * selectedTabIndex) + ((tabWidth - targetIndicatorWidth) / 2)

                val indicatorOffset by animateDpAsState(targetValue = targetIndicatorOffset, label = "offset")
                val indicatorWidth by animateDpAsState(targetValue = targetIndicatorWidth, label = "width")

                // 인디케이터
                Box(
                    modifier = Modifier
                        .offset(x = indicatorOffset)
                        .width(indicatorWidth)
                        .height(2.dp)
                        .background(color = DPlayTheme.colors.dplayBlack)
                )
            }
        }
    }
}

@Composable
private fun RegisteredMusicList(
    registeredMusicList: ImmutableList<RegisteredMusic>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(registeredMusicList) {
            DPlayMusicListItem(
                musicImageUrl = it.thumbnailUrl?:"",
                musicName = it.musicTitle,
                musicArtistName = it.artistName,
                musicContent = it.comment,
                onMoreClick = {},
                onClick = {}
            )
        }
    }
}

@Composable
private fun BookmarkedMusicList(
    bookmarkedMusicList: ImmutableList<BookmarkedMusic>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(bookmarkedMusicList) {
            DPlayMusicGridItem(
                musicImageUrl = it.thumbnailUrl?:"",
                musicName = it.musicTitle,
                musicArtistName = it.artistName,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    var uiState by remember {
        mutableStateOf(
            MyPageContract.MyPageState(
                userNickname = "디플레이",
                registeredMusicList = dummyRegisteredMusicList,
                bookmarkedMusicList = dummyBookmarkedMusicList,
            )
        )
    }

    DPlayTheme {
        MyPageScreen(
            state = uiState,
            onTabSelected = {
                uiState = uiState.copy(selectedTabIndex = it)
                Log.d("selectedTabIndex", it.toString())
            }
        )
    }
}

val dummyRegisteredMusicList = persistentListOf(
    RegisteredMusic(
        id = 1L,
        musicTitle = "Supernova",
        artistName = "aespa",
        comment = "운동할 때 들으면 힘나는 노래!",
        thumbnailUrl = null
    ),
    RegisteredMusic(
        id = 2L,
        musicTitle = "Magnetic",
        artistName = "ILLIT",
        comment = "요즘 제일 많이 듣는 곡",
        thumbnailUrl = null
    ),
    RegisteredMusic(
        id = 3L,
        musicTitle = "SPOT! (feat. JENNIE)",
        artistName = "지코 (ZICO)",
        comment = "비트가 너무 좋음",
        thumbnailUrl = null
    ),
    RegisteredMusic(
        id = 4L,
        musicTitle = "해야 (HEYA)",
        artistName = "IVE (아이브)",
        comment = "반복 재생 중...",
        thumbnailUrl = null
    ),
    RegisteredMusic(
        id = 5L,
        musicTitle = "소나기",
        artistName = "이클립스 (ECLIPSE)",
        comment = "선재 업고 튀어 OST",
        thumbnailUrl = null
    ),
    RegisteredMusic(
        id = 6L,
        musicTitle = "Love wins all",
        artistName = "아이유 (IU)",
        comment = "자기 전에 듣기 좋아요",
        thumbnailUrl = null
    )
)

// 2. 보관함 더미 데이터 (13개)
val dummyBookmarkedMusicList = persistentListOf(
    BookmarkedMusic(
        id = 101L,
        musicTitle = "How Sweet",
        artistName = "NewJeans",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 102L,
        musicTitle = "Bubble Gum",
        artistName = "NewJeans",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 103L,
        musicTitle = "나는 아픈 건 딱 질색이니까",
        artistName = "(여자)아이들",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 104L,
        musicTitle = "첫 만남은 계획대로 되지 않아",
        artistName = "TWS (투어스)",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 105L,
        musicTitle = "밤양갱",
        artistName = "비비 (BIBI)",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 106L,
        musicTitle = "EASY",
        artistName = "LE SSERAFIM",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 107L,
        musicTitle = "Smart",
        artistName = "LE SSERAFIM",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 108L,
        musicTitle = "Drama",
        artistName = "aespa",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 109L,
        musicTitle = "To. X",
        artistName = "태연 (TAEYEON)",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 110L,
        musicTitle = "Perfect Night",
        artistName = "LE SSERAFIM",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 111L,
        musicTitle = "Love 119",
        artistName = "RIIZE",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 112L,
        musicTitle = "Get A Guitar",
        artistName = "RIIZE",
        thumbnailUrl = null
    ),
    BookmarkedMusic(
        id = 113L,
        musicTitle = "Ditto",
        artistName = "NewJeans",
        thumbnailUrl = null
    )
)