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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.Canvas
import com.example.langio.useful.ExamHeader

data class WordPairConnect(
    val spanish: String,
    val english: String,
    var isMatched: Boolean = false
)

data class WordPosition(
    val word: String,
    val position: Offset
)

class ConnectWordsViewModel : ViewModel() {
    val wordPairs = listOf(
        WordPairConnect("hablar", "speak"),
        WordPairConnect("descansar", "talk"),
        WordPairConnect("moriartar", "move"),
        WordPairConnect("micansar", "act"),
        WordPairConnect("llamarsar", "sleep")
    )
}

@Composable
fun ConnectWordsScreen(
    viewModel: ConnectWordsViewModel = viewModel()
) {
    var selectedSpanishWord by remember { mutableStateOf<String?>(null) }
    var selectedEnglishWord by remember { mutableStateOf<String?>(null) }
    val spanishPositions = remember { mutableStateMapOf<String, Offset>() }
    val englishPositions = remember { mutableStateMapOf<String, Offset>() }
    val connections = remember { mutableStateListOf<Pair<String, String>>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C))
            .padding(16.dp)
    ) {
        ExamHeader(modifier = Modifier.fillMaxWidth())

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Lines Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                connections.forEach { (spanish, english) ->
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
                        color = Color.White,
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
                    viewModel.wordPairs.forEach { pair ->
                        Text(
                            text = pair.spanish,
                            color = if (selectedSpanishWord == pair.spanish) Color.Yellow else Color.White,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .clickable {
                                    selectedSpanishWord = pair.spanish
                                    if (selectedEnglishWord != null) {
                                        connections.add(pair.spanish to selectedEnglishWord!!)
                                        selectedSpanishWord = null
                                        selectedEnglishWord = null
                                    }
                                }
                                .onGloballyPositioned { coordinates ->
                                    val position = coordinates.positionInWindow()
                                    spanishPositions[pair.spanish] = Offset(
                                        position.x + coordinates.size.width,
                                        position.y + (coordinates.size.height / 2)
                                    )
                                }
                        )
                    }
                }

                // English words column
                Column {
                    viewModel.wordPairs.forEach { pair ->
                        Text(
                            text = pair.english,
                            color = if (selectedEnglishWord == pair.english) Color.Yellow else Color.White,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .clickable {
                                    selectedEnglishWord = pair.english
                                    if (selectedSpanishWord != null) {
                                        connections.add(selectedSpanishWord!! to pair.english)
                                        selectedSpanishWord = null
                                        selectedEnglishWord = null
                                    }
                                }
                                .onGloballyPositioned { coordinates ->
                                    val position = coordinates.positionInWindow()
                                    englishPositions[pair.english] = Offset(
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
            onClick = {
                connections.clear()
                selectedSpanishWord = null
                selectedEnglishWord = null
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF673AB7)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Hint",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Hint",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

