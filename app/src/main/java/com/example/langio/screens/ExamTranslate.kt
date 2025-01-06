package com.example.langio.screens

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.langio.R
import com.example.langio.controllers.GameController
import com.example.langio.useful.BackToLevelMenuButton
import com.example.langio.useful.ExamCard
import com.example.langio.useful.HeaderBar
import com.example.langio.useful.Hint
import java.util.Locale

@Composable
fun ExamTranslate (modifier: Modifier = Modifier) {

    val hints = remember { mutableStateListOf<Pair<Char, Boolean>>() }

    val context = LocalContext.current
    val isFirstHintActive = remember { mutableStateOf<Boolean>(false) }

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
            InputBox(modifier, isFirstHintActive, hints)
//            Spacer(modifier = Modifier.height(20.dp))
//            BackToLevelMenuButton()
            Hint(
                onClick = {
                    println(hints)
                    println()
                    takeATranslateHint(isFirstHintActive, hints)
                }
            )
        }
    }
}




@Composable
fun InputBox(
    modifier: Modifier = Modifier,
    isFirstHintActive: MutableState<Boolean>,
    hints: SnapshotStateList<Pair<Char, Boolean>>
) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var hintText by remember { mutableStateOf("") }

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

    Column(modifier = modifier.padding(horizontal = 16.dp).fillMaxHeight(.7f)) {
        TextField(
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            label = { Text("Enter translation") },
            placeholder = { Text("Enter here...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(Color.White),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    println("Entered translation: ${inputText.text}")
                }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = hintText,
                color = Color.Yellow,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(8.dp)
            )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8559A5)),
                onClick = {
                    println("Entered translation: ${inputText.text}")
                    val correctWord = GameController.instance.currentScreenWordsToBeUsed?.get(0)?.spanishWord?.lowercase(Locale.ROOT)
                    if (inputText.text.lowercase(Locale.ROOT) == correctWord) {
                        println("CORRECT!!!")
                        correctTranslation()
                    }
                    else {
                        println("INCORRECT!!! ${inputText.text} != ${correctWord}")
                        incorrectTranslation()
                    }
                },
            ) {
                Text(
                    text = "Check",
                    color = Color(0xFFF5F5F5),
                )
            }
        }

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
            char to true // Spacja, przypisuje true
        } else {
            char to false // Inny znak, przypisuje false
        }
    }
}

fun incorrectTranslation() {
    GameController.instance.decreaseLivesNumber()
}

fun correctTranslation() {
    GameController.instance.nextExamScreen(1)
}
