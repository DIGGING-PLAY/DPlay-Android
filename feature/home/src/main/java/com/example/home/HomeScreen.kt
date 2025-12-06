package com.example.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.component.DPlayLargeCover
import com.example.designsystem.component.DPlaySubjectItem
import com.example.designsystem.component.DplayClickableIcon
import com.example.designsystem.component.DplayLogoTopAppBar
import com.example.designsystem.theme.DPlayTheme
import kotlinx.coroutines.flow.collectLatest
import com.dplay.designsystem.R

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeContract.HomeIntent.Initialize)
    }

    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is HomeContract.HomeSideEffect.ShowSnackBar -> {

                }
            }
        }
    }

    HomeScreen(

    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.HomeState = HomeContract.HomeState()
) {
    val colors =  DPlayTheme.colors
    val typography = DPlayTheme.typography
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DplayLogoTopAppBar(
            onListClick = {
                //TODO: [과거 추천 기록] 뷰로 이동
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = uiState.todayQuestion.dateText , style = typography.titleBold18, color = colors.dplayBlack)
            DplayClickableIcon(
                iconRes = R.drawable.ic_refresh_20,
                modifier = Modifier.padding(16.dp),
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        DPlaySubjectItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            subject = uiState.todayQuestion.title,
        )
        Spacer(modifier = Modifier.height(44.dp))
        DPlayLargeCover(
            isBookmarkChecked = true,
            isLikeChecked = false,
            likeCount = 24,
            writerProfileImageUrl = "",
            writerNickname = "윤서얌",
            content = "진짜 나오자마자 들었는데 이 노래가 최고! 출근곡, 퇴근곡, 노동곡 다 되는 짱제로! 일하는 매장에서도 수십 번씩 틀고 있어요. 모두가 알아야 돼..",
            musicImageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music221/v4/53/d4/c1/53d4c1e4-c712-ef2b-e862-f46045fe5500/cover_KM0022858_1.jpg/512x512bb.jpg",
            onStreamClick = {},
            onLikeClick = {},
            onBookmarkClick = {},
            isStreaming = true,
            modifier = Modifier.padding(horizontal = 48.dp)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun HomePreview() {
   DPlayTheme {
       HomeScreen()
   }
}
