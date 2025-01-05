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
import androidx.compose.runtime.mutableStateListOf
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
val HINTED_ANSWER_COLOR = Color(0xFFBA8DC2)
val CORRECT_ANSWER_COLOR = Color(0xFF87C762)
val INCORRECT_ANSWER_COLOR = Color(0xFFCB7E7E)

@Composable
fun ExamChoice (modifier: Modifier = Modifier) {

    val hintKnownIncorrect = remember { mutableStateListOf<String>() }
    val knownIncorrect = remember { mutableStateListOf<String>() }
    val answers = remember { mutableStateListOf<Pair<String, Boolean>>() }


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

            GameController.instance.currentScreenWordsToBeUsed?.let {
                answers.clear()
                answers.addAll(getShuffledAnswersTab(it[0].spanishWord, it[0].incorrectSpanishWords))
                Answers(answers, hintKnownIncorrect, knownIncorrect)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Hint(onClick = {
                takeAChoiceHint(hintKnownIncorrect, knownIncorrect, answers)
            })
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
fun Answers(
    answers: List<Pair<String, Boolean>>,
    hintKnownIncorrect: MutableList<String>,
    knownIncorrect: MutableList<String>
)
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
//                        if (answers[0].second) {
//                            correctAnswer(0)
//                            buttonAColor.value = CORRECT_ANSWER_COLOR
//                        }
//                        else {
//                            incorrectAnswer(0, answers)
//                            buttonAColor.value = INCORRECT_ANSWER_COLOR
//                        }
                        if (answers[0].second) {
                            buttonAColor.value = CORRECT_ANSWER_COLOR
                            correctAnswer(0)
                        }
                        else if (!hintKnownIncorrect.contains(answers[0].first) && !knownIncorrect.contains(answers[0].first)) {
                            incorrectAnswer(0, answers)
                            knownIncorrect.add(answers[0].first)
                        }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        when {
                            knownIncorrect.contains(answers[0].first) -> INCORRECT_ANSWER_COLOR
                            hintKnownIncorrect.contains(answers[0].first) -> HINTED_ANSWER_COLOR
                            else -> buttonAColor.value
                        }),
//                            if (buttonAColor.value != CORRECT_ANSWER_COLOR && buttonAColor.value == IDLE_ANSWER_COLOR)
//                                buttonAColor.value
//                            else
//                                    (if (knownIncorrect.contains(answers[0].first))
//                                        INCORRECT_ANSWER_COLOR
//                                    else
//                                        HINTED_ANSWER_COLOR)),
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
//                        if (answers[1].second) {
//                            correctAnswer(1)
//                            buttonBColor.value = CORRECT_ANSWER_COLOR
//
//                        }
//                        else {
//                            incorrectAnswer(1, answers)
//                            buttonBColor.value = INCORRECT_ANSWER_COLOR
//                        }
                        if (answers[1].second) {
                            buttonBColor.value = CORRECT_ANSWER_COLOR
                            correctAnswer(1)
                        }
                        else if (!hintKnownIncorrect.contains(answers[1].first) && !knownIncorrect.contains(answers[1].first)) {
                            incorrectAnswer(1, answers)
                            knownIncorrect.add(answers[1].first)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        when {
                            knownIncorrect.contains(answers[1].first) -> INCORRECT_ANSWER_COLOR
                            hintKnownIncorrect.contains(answers[1].first) -> HINTED_ANSWER_COLOR
                            else -> buttonBColor.value
                        }),
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
//                        if (answers[2].second) {
//                            correctAnswer(2)
//                            buttonCColor.value = CORRECT_ANSWER_COLOR
//                        }
//                        else {
//                            incorrectAnswer(2, answers)
//                            buttonCColor.value = INCORRECT_ANSWER_COLOR
//                        }

                        if (answers[2].second) {
                            buttonCColor.value = CORRECT_ANSWER_COLOR
                            correctAnswer(2)
                        }
                        else if (!hintKnownIncorrect.contains(answers[2].first) && !knownIncorrect.contains(answers[2].first)) {
                            incorrectAnswer(2, answers)
                            knownIncorrect.add(answers[2].first)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        when {
                            knownIncorrect.contains(answers[2].first) -> INCORRECT_ANSWER_COLOR
                            hintKnownIncorrect.contains(answers[2].first) -> HINTED_ANSWER_COLOR
                            else -> buttonCColor.value
                        }),

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
//                        if (answers[3].second) {
//                            correctAnswer(3)
//                            buttonDColor.value = CORRECT_ANSWER_COLOR
//                        }
//                        else {
//                            incorrectAnswer(3, answers)
//                            buttonDColor.value = INCORRECT_ANSWER_COLOR
//                        }
                        if (answers[3].second) {
                            buttonDColor.value = CORRECT_ANSWER_COLOR
                            correctAnswer(3)
                        }
                        else if (!hintKnownIncorrect.contains(answers[3].first) && !knownIncorrect.contains(answers[3].first)) {
                            incorrectAnswer(3, answers)
                            knownIncorrect.add(answers[3].first)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        when {
                            knownIncorrect.contains(answers[3].first) -> INCORRECT_ANSWER_COLOR
                            hintKnownIncorrect.contains(answers[3].first) -> HINTED_ANSWER_COLOR
                            else -> buttonDColor.value
                        }),
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

fun takeAChoiceHint(
    hintKnownIncorrect: MutableList<String>,
    knownIncorrect: MutableList<String>,
    answers: MutableList<Pair<String, Boolean>>
) {
    if (GameController.instance.hintNumber > 0 && hintKnownIncorrect.size + knownIncorrect.size < 3) {
        GameController.instance.decreaseHintNumber()
        val hint = answers.shuffled().firstOrNull { pair ->
            !pair.second && !knownIncorrect.contains(pair.first) && !hintKnownIncorrect.contains(pair.first)
        }
        hint?.let {
            hintKnownIncorrect.add(it.first)
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
