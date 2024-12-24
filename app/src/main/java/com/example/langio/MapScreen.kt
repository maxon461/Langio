package com.example.langio

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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
    navController: NavController,
    selectedTab: String = "map",
    modifier: Modifier = Modifier,
    gameMapViewModel: GameMapViewModel = viewModel()
) {
    val currentChapter by gameMapViewModel.currentChapter.collectAsState()

    Scaffold(
        bottomBar = {
            CustomBottomNavigationBar(
                navController = navController,
                selectedTab = selectedTab
            )
        },
        topBar = {
            HeaderBar(
                modifier = modifier,
                showPfp = true,
                showLevel = false
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
}

@Composable
fun LevelProgressionMap(
    levels: List<LevelInfo>,
    onLevelClick: (LevelInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            levels.windowed(2).forEachIndexed { index, (current, next) ->
                val horizontalSpacing = size.width * 0.3f
                val startX = size.width * 0.4f + (if (index % 2 == 0) 0f else horizontalSpacing)
                val endX = size.width * 0.4f + (if ((index + 1) % 2 == 0) 0f else horizontalSpacing)
                val verticalSpacing = 120f
                val startY = (index * verticalSpacing) + 150f
                val endY = ((index + 1) * verticalSpacing) + 150f

                val path = Path().apply {
                    moveTo(startX, startY)
                    cubicTo(
                        startX, startY + verticalSpacing * 0.3f,
                        endX, endY - verticalSpacing * 0.3f,
                        endX, endY
                    )
                }

                drawPath(
                    path = path,
                    color = if (next.isUnlocked) Color(0xFF9333EA) else Color.Gray,
                    style = Stroke(width = 4f)
                )
            }
        }

        levels.forEachIndexed { index, level ->
            val reversedIndex = levels.size - 1 - index  // Reverse the index
            val baseY = 50
            val spacing = 110

            LevelNode(
                level = level,
                modifier = Modifier
                    .offset(
                        x = if (reversedIndex % 2 == 0) 120.dp else 240.dp,
                        y = ((reversedIndex * spacing + baseY).dp)
                    ),
                onClick = { onLevelClick(level) }
            )
        }
    }
}

@Composable
fun LevelNode(
    level: LevelInfo,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
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
            color = Color.White,
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
                LevelInfo(6, "Level 1", true),
                LevelInfo(7, "Level 2", false),
                LevelInfo(8, "Level 3", false),
                LevelInfo(9, "Level 4", false),
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
        // Handle level selection
        // You can implement navigation to the level screen here
    }
}