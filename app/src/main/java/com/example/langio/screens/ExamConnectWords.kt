package com.example.langio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import com.example.langio.controllers.GameController
import com.example.langio.useful.HeaderBar
import com.example.langio.useful.Hint

data class WordPairConnect(
    val spanish: String,
    val english: String,
    var isMatched: Boolean = false
)

data class WordPosition(
    val word: String,
    val position: Offset
)



@Composable
fun ExamConnectWordsScreen(
//    viewModel: ConnectWordsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var selectedSpanishWord by remember { mutableStateOf<String?>(null) }
    var selectedEnglishWord by remember { mutableStateOf<String?>(null) }
    val spanishPositions = remember { mutableStateMapOf<String, Offset>() }
    val englishPositions = remember { mutableStateMapOf<String, Offset>() }

    val connections = remember { mutableStateListOf<Pair<String, String>>() }

    val hints = remember{ mutableStateListOf<Pair<String, String>>() }

    val correctPairs = GameController.instance.currentScreenWordsToBeUsed
        ?.subList(0, 4)
        ?.shuffled()
        ?.map { word -> word.englishWord to word.spanishWord }

    val displayedPairs = correctPairs?.map { it.first to it.second }?.let { pairs ->
        val shuffledSecondWords = pairs.map { it.second }.shuffled()
        pairs.mapIndexed { index, pair -> pair.first to shuffledSecondWords[index] }
    }


    Scaffold(
        topBar = { HeaderBar(modifier, showLevel = true, showExam = true, showLives = true) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2C2C2C))
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center

            ) {
                // Lines Canvas
                Canvas(modifier = Modifier
//                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                ) {
                    connections.forEach { (english, spanish) ->
                        val startPos = spanishPositions[spanish] ?: return@forEach
                        val endPos = englishPositions[english] ?: return@forEach

                        val adjustedStartPos = Offset(startPos.x, startPos.y - 300f)
                        val adjustedEndPos = Offset(endPos.x - 80, endPos.y - 300f)

                        val path = Path().apply {
                            moveTo(adjustedStartPos.x, adjustedStartPos.y)

                            val controlPoint1X = adjustedStartPos.x - 50f
                            val controlPoint2X = adjustedEndPos.x - 50f
                            val controlPointY = (adjustedStartPos.y + adjustedEndPos.y) / 2 - 50f

                            cubicTo(
                                controlPoint1X, controlPointY,
                                controlPoint2X, controlPointY,
                                adjustedEndPos.x, adjustedEndPos.y
                            )
                        }

                        drawPath(
                            path = path,
                            color = if (hints.any { it.first == english && it.second == spanish }) {
                                Color.Green
                            } else {
                                Color.White
                            },
                            style = Stroke(
                                width = 8f,
                                cap = StrokeCap.Round
                            )
                        )
                    }
                }

                // Words
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Spanish words column
                    Column {
                        displayedPairs?.forEach { (enWord, esWord) ->
                            Text(
                                text = esWord,
                                color = if (selectedSpanishWord == esWord) Color.Yellow else Color.White,
                                fontSize = 24.sp,
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .clickable {
                                        selectedSpanishWord = esWord
                                        if (selectedEnglishWord != null) {
                                            connections.removeAll { it.first == selectedEnglishWord!! || it.second == esWord }
                                            connections.add(selectedEnglishWord!! to esWord)
                                            selectedSpanishWord = null
                                            selectedEnglishWord = null
                                        }
                                    }
                                    .onGloballyPositioned { coordinates ->
                                        val position = coordinates.positionInWindow()
                                        spanishPositions[esWord] = Offset(
                                            position.x + coordinates.size.width,
                                            position.y + (coordinates.size.height / 2)
                                        )
                                    }
                            )
                        }
                    }

                    // English words column
                    Column {
                        displayedPairs?.forEach { (enWord, esWord) ->
                            Text(
                                text = enWord,
                                color = if (selectedEnglishWord == enWord) Color.Yellow else Color.White,
                                fontSize = 24.sp,
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .clickable {
                                        selectedEnglishWord = enWord
                                        if (selectedSpanishWord != null) {
                                            connections.removeAll { it.second == selectedSpanishWord!! || it.first == enWord}
                                            connections.add(enWord to selectedSpanishWord!!)
                                            selectedSpanishWord = null
                                            selectedEnglishWord = null
                                        }
                                    }
                                    .onGloballyPositioned { coordinates ->
                                        val position = coordinates.positionInWindow()
                                        englishPositions[enWord] = Offset(
                                            position.x,
                                            position.y + (coordinates.size.height / 2)
                                        )
                                    }
                            )
                        }
                    }
                }
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8559A5)),
                onClick = {
                    if (connections.toSet() == correctPairs?.toSet()) {
                        correctConnection()
                    }
                    else {
                        incorrectConnection()
                    }
                    println(displayedPairs)
                    println(correctPairs)
                    println(connections)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Check",
                    color = Color(0xFFF5F5F5),
                )
            }

            Hint (
                onClick = {
                    selectedSpanishWord = null
                    selectedEnglishWord = null
                    takeAConnectionsHint(connections, hints, correctPairs)
                }
            )


        }
    }
}

fun takeAConnectionsHint(
    connections: SnapshotStateList<Pair<String, String>>,
    hints: SnapshotStateList<Pair<String, String>>,
    correctPairs: List<Pair<String, String>>?
) {
    if (hints.size >= 4 || GameController.instance.hintNumber <= 0) return

    GameController.instance.decreaseHintNumber()
    correctPairs?.shuffled()?.firstOrNull { pair ->
        pair !in hints
    }?.let { newHint ->
        hints.add(newHint)

        val (first, second) = newHint

        connections.removeAll { it.first == first || it.second == second }
        connections.add(newHint)
    }
}

fun incorrectConnection() {
    GameController.instance.decreaseLivesNumber()
    println("INCORRECT CONNECTION")
}

fun correctConnection() {
    println("CORRECT")
    GameController.instance.nextExamScreen(4)
}
