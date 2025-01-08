package com.example.langio.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langio.controllers.GameController
import com.example.langio.useful.ExamCard
import com.example.langio.useful.HeaderBar
import com.example.langio.useful.Hint
import java.text.Normalizer
import java.util.Locale


const val MAX_CHAR_COUNT = 30

@Composable
fun ExamTranslate (modifier: Modifier = Modifier) {

    val hints = remember { mutableStateListOf<Pair<Char, Boolean>>() }

    val context = LocalContext.current
    val isFirstHintActive = remember { mutableStateOf<Boolean>(false) }

    val answerState = remember { mutableStateOf<GameController.AnswerState>(GameController.AnswerState.IDLE) }

    Scaffold(
        topBar = { HeaderBar(modifier, showLevel = true, showExam = true, showLives = true) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
//            ExamCard("???", painterResource(R.drawable.cow))
            GameController.instance.currentScreenWordsToBeUsed?.get(0)
                ?.let {
                    println(it.englishWord)
                    ExamCard(it.englishWord, painterResource(context.resources.getIdentifier("${it.englishWord}_img", "raw", "com.example.langio"))) }

            val esWord = GameController.instance.currentScreenWordsToBeUsed?.get(0)?.spanishWord
            LaunchedEffect(esWord) {
                esWord?.let {
                    hints.clear()
                    hints.addAll(createHintsFromWord(it))
                }
            }
            InputBox(modifier, isFirstHintActive, hints, answerState)
//            Spacer(modifier = Modifier.height(20.dp))
//            BackToLevelMenuButton()

        }
    }
}




@Composable
fun InputBox(
    modifier: Modifier = Modifier,
    isFirstHintActive: MutableState<Boolean>,
    hints: SnapshotStateList<Pair<Char, Boolean>>,
    answerState: MutableState<GameController.AnswerState>
) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var hintText by remember { mutableStateOf("") }

    val labelColor by animateColorAsState(
        targetValue = when (answerState.value) {
            GameController.AnswerState.IDLE -> Color.White
            GameController.AnswerState.CORRECT -> Color.Green
            GameController.AnswerState.INCORRECT -> Color.Red
        }
    )

    hintText = if (isFirstHintActive.value) {
        if (hints.any { it.second }) {
            val text = hints.joinToString(" ") {
                if (it.second) it.first.toString() else "_"
            }
            "HINT: $text"
        } else {
            "HINT: ${"_ ".repeat(hints.size).trim()}"
        }
    } else {
        ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Input and Hint Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = inputText,
                onValueChange = { newText ->
                    if (newText.text.length <= MAX_CHAR_COUNT) {
                        inputText = newText
                        answerState.value = GameController.AnswerState.IDLE
                    }
                },
                label = {
                    Text(
                        text = "Enter translation",
                        color = when (answerState.value) {
                            GameController.AnswerState.IDLE -> Color.Gray
                            GameController.AnswerState.CORRECT -> Color.Green
                            GameController.AnswerState.INCORRECT -> Color.Red
                        }
                    )
                },
                placeholder = { Text("Enter here...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        println("Entered translation: ${inputText.text}")
                    }
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Hint Text Row
            Text(
                text = hintText,
                color = Color.Yellow,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
        }

        // Buttons Section at the Bottom
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8559A5)),
                onClick = {
                    println("Entered translation: ${inputText.text}")
                    val correctWord = GameController.instance.currentScreenWordsToBeUsed?.get(0)?.spanishWord?.lowercase(Locale.ROOT)
                    println(normalizeString(correctWord ?: "").lowercase(Locale.ROOT))
                    if (normalizeString(inputText.text.lowercase(Locale.ROOT)) == normalizeString(correctWord ?: "").lowercase(Locale.ROOT)) {
                        println("CORRECT!!!")
                        correctTranslation(answerState)
                    } else {
                        println("INCORRECT!!! ${inputText.text} != ${correctWord}")
                        answerState.value = GameController.AnswerState.INCORRECT
                        GameController.instance.decreaseLivesNumber()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Check",
                    color = Color(0xFFF5F5F5),
                    fontSize = 18.sp
                )
            }

            Hint(
                onClick = {
                    println(hints)
                    takeATranslateHint(isFirstHintActive, hints)
                },

            )
        }
    }

    BackHandler {
        println("BACK PRESSED")
        // Handle back press here if needed
    }
}

fun takeATranslateHint(
    isFirstHintActive: MutableState<Boolean>,
    hints: SnapshotStateList<Pair<Char, Boolean>>
) {
    if (GameController.instance.hintNumber > 0) {
        if (!isFirstHintActive.value) {
            isFirstHintActive.value = true
            GameController.instance.decreaseHintNumber()
        }
        else {
            println("befoer: ${hints}")
            if (activateRandomHint(hints)) {
                println("after: ${hints}")
                GameController.instance.decreaseHintNumber()
            }
        }
    }
}

fun activateRandomHint(hints: SnapshotStateList<Pair<Char, Boolean>>): Boolean {
    val falseHints = hints.withIndex().filter { !it.value.second }

    if (falseHints.isNotEmpty()) {
        val randomIndex = falseHints.random().index

        hints[randomIndex] = hints[randomIndex].copy(second = true)
        return true
    }
    return false
}

fun createHintsFromWord(word: String): List<Pair<Char, Boolean>> {
    return word.map { char ->
        if (char == ' ') {
            char to true
        } else {
            char to false
        }
    }
}

fun incorrectTranslation(answerState: MutableState<GameController.AnswerState>) {
    answerState.value = GameController.AnswerState.INCORRECT
    GameController.instance.decreaseLivesNumber()
}

fun correctTranslation(answerState: MutableState<GameController.AnswerState>) {
    answerState.value = GameController.AnswerState.CORRECT
    GameController.instance.nextExamScreen(1)
}


fun normalizeString(input: String): String {
    return Normalizer.normalize(input, Normalizer.Form.NFD)
        .replace(Regex("[^\\p{ASCII}]"), "") // Usuwamy znaki spoza ASCII, takie jak akcenty
}
