package com.example.langio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langio.R
import com.example.langio.controllers.GameController
import com.example.langio.useful.BackToLevelMenuButton
import com.example.langio.useful.ExamCard
import com.example.langio.useful.HeaderBar
import com.example.langio.useful.Hint
import com.example.langio.useful.OneSidedHorizontalRoundedRectangle


val IDLE_ANSWER_COLOR = Color(0xFFE8DEF8)
val CORRECT_ANSWER_COLOR = Color.Green
val INCORRECT_ANSWER_COLOR = Color.Red

@Composable
fun ExamChoice (modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { HeaderBar(modifier, showLevel = true, showExam = true, showLives = true) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
            GameController.instance.currentScreenWordsToBeUsed?.get(0)
                ?.let { ExamCard(it.englishWord, painterResource(R.drawable.cow)) }

            GameController.instance.currentScreenWordsToBeUsed?.let { Answers(getShuffledAnswersTab(it[0].spanishWord, it[0].incorrectSpanishWords)) }
//            GameController.instance.currentScreenWordsToBeUsed?.let { Answers(it[0].incorrectSpanishWords.shuffled()) }

            Spacer(modifier = Modifier.height(20.dp)) // Add some spacing
            BackToLevelMenuButton() // Add the button here
            Hint()
        }
    }
}


fun getShuffledAnswersTab(correctWord: String, incorrectWords: List<String>): List<Pair<String, Boolean>> {
    val answersList: MutableList<Pair<String, Boolean>> = mutableListOf()

    for (item in incorrectWords.shuffled().take(3)) {
        answersList.add(item to false)
    }
    answersList.add(correctWord to true)
    return answersList.shuffled()
}

@Composable
fun Answers(answers: List<Pair<String, Boolean>>)
{
    val buttonAColor = remember { mutableStateOf(IDLE_ANSWER_COLOR) }
    val buttonBColor = remember { mutableStateOf(IDLE_ANSWER_COLOR) }
    val buttonCColor = remember { mutableStateOf(IDLE_ANSWER_COLOR) }
    val buttonDColor = remember { mutableStateOf(IDLE_ANSWER_COLOR) }

    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(.7f),
        contentAlignment = Alignment.Center
    )
    {
        Column (
            modifier = Modifier
                .fillMaxWidth(.95f)
                .fillMaxHeight(.8f)
        ){
            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(true),
                    onClick = {
                        if (answers[0].second) {
                            correctAnswer(0)
                            buttonAColor.value = CORRECT_ANSWER_COLOR
                        }
                        else {
                            incorrectAnswer(0, answers)
                            buttonAColor.value = INCORRECT_ANSWER_COLOR
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonAColor.value),
                ) {
                    Text(
                        text = answers[0].first,
                        color = Color(0xFF79747E),
                        fontSize = 25.sp,
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(false),
                    onClick = {
                        if (answers[1].second) {
                            correctAnswer(1)
                            buttonBColor.value = CORRECT_ANSWER_COLOR

                        }
                        else {
                            incorrectAnswer(1, answers)
                            buttonBColor.value = INCORRECT_ANSWER_COLOR
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBColor.value),
                    ) {
                    Text(
                        text = answers[1].first,
                        color = Color(0xFF79747E),
                        fontSize = 25.sp
                        )
                }
            }

            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(true),
                    onClick = {
                        if (answers[2].second) {
                            correctAnswer(2)
                            buttonCColor.value = CORRECT_ANSWER_COLOR
                        }
                        else {
                            incorrectAnswer(2, answers)
                            buttonCColor.value = INCORRECT_ANSWER_COLOR
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonCColor.value),

                    ) {
                    Text(
                        text = answers[2].first,
                        color = Color(0xFF79747E),
                        fontSize = 25.sp
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(false),
                    onClick = {
                        if (answers[3].second) {
                            correctAnswer(3)
                            buttonDColor.value = CORRECT_ANSWER_COLOR
                        }
                        else {
                            incorrectAnswer(3, answers)
                            buttonDColor.value = INCORRECT_ANSWER_COLOR
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonDColor.value),

                    ) {
                    Text(
                        text = answers[3].first,
                        color = Color(0xFF79747E),
                        fontSize = 25.sp
                    )
                }
            }
        }
    }

}

fun incorrectAnswer(i: Int, answers: List<Pair<String, Boolean>>) {
    println("INCORRECT")
    GameController.instance.decreaseLivesNumber()
}

fun correctAnswer(i: Int) {
    println("CORRECT")
    GameController.instance.nextExamScreen(1)
}
