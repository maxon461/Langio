package com.example.langio.screens

import androidx.activity.compose.BackHandler
import androidx.annotation.Nullable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.ui.layout.positionInWindow
import com.example.langio.controllers.GameController
import com.example.langio.useful.*
import org.junit.Test.None



data class LevelInfo(
    val id: Int,
    val name: String,
    val isUnlocked: Boolean,
    val isReward: Boolean = false
)

data class ChapterInfo(
    val id: Int,
    val name: String,
    val subtitle: String,
    val levels: List<LevelInfo>
)


@Composable
fun MapScreen(
//    navController: NavController,
    selectedTab: String = "map",
    modifier: Modifier = Modifier,
    gameMapViewModel: GameMapViewModel = viewModel()
) {
    val currentChapter by gameMapViewModel.currentChapter.collectAsState()
//    gameMapViewModel.navController = navController

    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
//                navController = navController,
                selectedTab = selectedTab
            )
        },
        topBar = {
            HeaderBar(
                modifier = modifier,
                showPfp = true,
                showLevel = false,
                showExam = false
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF403E3E))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LevelProgressionMap(
                    levels = currentChapter.levels,
                    onLevelClick = gameMapViewModel::onLevelSelected,
                    modifier = Modifier.weight(1f)
                )

                ChapterNavigation(
                    chapter = currentChapter,
                    onPreviousChapter = gameMapViewModel::onPreviousChapter,
                    onNextChapter = gameMapViewModel::onNextChapter,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    BackHandler {
        println("BACK PRESSED")
//      DO NOTHING
    }
}

@Composable
fun LevelProgressionMap(
    levels: List<LevelInfo>,
    onLevelClick: (LevelInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val levelPositions = remember { mutableStateListOf<Offset>() }
    val unlockedLevelId = GameController.instance.unlockedLevelId

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    Box(modifier = modifier.fillMaxSize()) {
        if (levelPositions.size >= 2) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                levelPositions.windowed(2).forEachIndexed { index, (start, end) ->
                    val controlPointOffset = screenHeightPx * 0.083f
                    val heightIncrement = screenHeightPx * 0.013f * index

                    val controlPoint1 = Offset(
                        (start.x + end.x) / 2f,
                        start.y - controlPointOffset - heightIncrement
                    )
                    val controlPoint2 = Offset(
                        (start.x + end.x) / 2f,
                        end.y - controlPointOffset - heightIncrement
                    )

                    val path = Path().apply {
                        moveTo(
                            start.x + screenWidthPx * 0.046f,
                            start.y - screenHeightPx * 0.063f - heightIncrement
                        )
                        cubicTo(
                            controlPoint1.x,
                            controlPoint1.y,
                            controlPoint2.x,
                            controlPoint2.y - screenHeightPx * 0.021f,
                            end.x,
                            end.y - screenHeightPx * 0.083f - heightIncrement
                        )
                    }

                    drawPath(
                        path = path,
                        color = if (levels[index + 1].id <= unlockedLevelId) Color(0xFF9333EA) else Color.Gray,
                        style = Stroke(
                            width = screenWidthPx * 0.004f,
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }

        levels.forEachIndexed { index, level ->
            val reversedIndex = levels.size - 1 - index

            val nodeX = if (reversedIndex % 2 == 0) {
                configuration.screenWidthDp * 0.2f
            } else {
                configuration.screenWidthDp * 0.6f
            }

            val baseY = configuration.screenHeightDp * 0.1f
            val verticalSpacing = configuration.screenHeightDp * 0.11f
            val nodeY = reversedIndex * verticalSpacing + baseY

            Box(
                modifier = Modifier
                    .offset(x = nodeX.dp, y = nodeY.dp)
                    .onGloballyPositioned { coordinates ->
                        val position = coordinates.positionInWindow()
                        val nodeSize = screenWidthPx * 0.03f
                        val centerPosition = Offset(
                            position.x + nodeSize,
                            position.y + nodeSize
                        )

                        if (index >= levelPositions.size) {
                            levelPositions.add(centerPosition)
                        } else {
                            levelPositions[index] = centerPosition
                        }
                    }
            ) {
                LevelNode(
                    level = level.copy(isUnlocked = level.id <= unlockedLevelId),
                    onClick = { onLevelClick(level) }
                )
            }
        }
    }
}





@Composable
fun LevelNode(
    level: LevelInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                if (level.isUnlocked) {
                    println("Level clicked: ${level.id}, navigating to LevelMenu")
                    try {
                        onClick()
                    } catch (e: Exception) {
                        println("Error during navigation: ${e.message}")
                    }
                }
            },
            enabled = level.isUnlocked,
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = if (level.isUnlocked) Color(0xFF9333EA) else Color.Gray,
                    shape = CircleShape
                )
        ) {
            if (level.isReward) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Reward",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Text(
                    text = "★",
                    color = Color.White,
                    fontSize = 32.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = level.name,
            color = if (level.isUnlocked) Color.White else Color.Gray,
            fontSize = 16.sp
        )
    }
}








@Composable
fun ChapterNavigation(
    chapter: ChapterInfo,
    onPreviousChapter: () -> Unit,
    onNextChapter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onPreviousChapter,
            modifier = Modifier.alpha(if (chapter.id > 1) 1f else 0.5f)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous Chapter",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chapter ${chapter.id}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = chapter.subtitle,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        IconButton(
            onClick = onNextChapter,
            modifier = Modifier.alpha(if (chapter.id < 2) 1f else 0.5f)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next Chapter",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

class GameMapViewModel : ViewModel() {
    //  ADDED IN ORDER TO NAVIGATE
    lateinit var navController: NavController
//  --------


    private val chapters = listOf(
        ChapterInfo(
            id = 1,
            name = "Chapter 1",
            subtitle = "Basics",
            levels = listOf(
                LevelInfo(1, "Level 1", true),
                LevelInfo(2, "Level 2", true),
                LevelInfo(3, "Level 3", true),
                LevelInfo(4, "Level 4", true),
                LevelInfo(5, "Reward", true, isReward = true)
            )
        ),
        ChapterInfo(
            id = 2,
            name = "Chapter 2",
            subtitle = "Advanced",
            levels = listOf(
                LevelInfo(6, "Level 6", true),
                LevelInfo(7, "Level 7", true),
                LevelInfo(8, "Level 8", false),
                LevelInfo(9, "Level 9", false),
                LevelInfo(10, "Reward", false, isReward = true)
            )
        )
    )

    private val _currentChapter = MutableStateFlow(chapters.first())
    val currentChapter: StateFlow<ChapterInfo> = _currentChapter.asStateFlow()

    fun onPreviousChapter() {
        val currentIndex = chapters.indexOf(_currentChapter.value)
        if (currentIndex > 0) {
            _currentChapter.value = chapters[currentIndex - 1]
        }
    }

    fun onNextChapter() {
        val currentIndex = chapters.indexOf(_currentChapter.value)
        if (currentIndex < chapters.size - 1) {
            _currentChapter.value = chapters[currentIndex + 1]
        }
    }

    fun onLevelSelected(level: LevelInfo) {
//        if (level.id % 5 == 0) {
//            println("Level ${level.id} is disabled. TODO: Add specific handling for reward levels.")
//            return
//        }

        try {
            GameController.instance.setCurrentLevel(level.id)
            GameController.instance.prepareDataForCurrentLevel()
            GameController.instance.changeScreen(GameController.Screen.LEVEL_MENU)
        } catch (e: Exception) {
            println("Navigation failed: ${e.message}")
        }
    }



}

