package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.dplay.designsystem.R
import com.example.designsystem.theme.DPlayTheme
import com.example.home.HomeScreen
import com.example.mypage.MyPageScreen
import com.example.recommend.RecommendScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface TopLevelRoute {
    @get:DrawableRes
    val selectedIconRes: Int
    @get:DrawableRes
    val unselectedIconRes: Int
}

data object Home : TopLevelRoute {
    override val selectedIconRes: Int
        get() = R.drawable.ic_home_active_32
    override val unselectedIconRes: Int
        get() = R.drawable.ic_home_disabled_32
}

data object MyPage : TopLevelRoute {
    override val selectedIconRes: Int
        get() = R.drawable.ic_bookmark_active_32
    override val unselectedIconRes: Int
        get() = R.drawable.ic_bookmark_disabled_32
}

data object Recommend

private val TOP_LEVEL_ROUTES : ImmutableList<TopLevelRoute> = persistentListOf(Home, MyPage)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val topLevelBackStack = remember { TopLevelBackStack<Any>(Home) }
            DPlayTheme {
                Scaffold(
                    modifier =
                        Modifier.navigationBarsPadding(),
                    bottomBar = {
                        BottomNavigationBar(
                            isVisible = true,
                            topLevelRouteList = TOP_LEVEL_ROUTES,
                            currentTab = topLevelBackStack.topLevelKey,
                            onBottomNavigationItemClick = { route ->
                                topLevelBackStack.addTopLevel(route)
                            },
                            onPlusButtonClick = {
                                topLevelBackStack.addTopLevel(Recommend)
                            },
                        )
                    },
                ) { padding ->
                    NavDisplay(
                        modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
                        backStack = topLevelBackStack.backStack,
                        onBack = { topLevelBackStack.removeLast() },
                        entryProvider = entryProvider {
                            entry<Home>{
                                HomeScreen()
                            }
                            entry<MyPage>{
                                MyPageScreen()
                            }
                            entry<Recommend>{
                                RecommendScreen()
                            }
                        }
                    )
                }
            }
        }
    }
}

class TopLevelBackStack<T: Any>(startKey: T) {

    private var topLevelStacks : LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() =
        backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }

    fun addTopLevel(key: T){
        if (topLevelStacks[key] == null){
            topLevelStacks[key] = mutableStateListOf(key)
        } else {
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: T){
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast(){
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}