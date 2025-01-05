package com.example.langio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.example.langio.R
import com.example.langio.controllers.GameController
import com.example.langio.useful.BackToLevelMenuButton
import com.example.langio.useful.ExamCard
import com.example.langio.useful.HeaderBar
import com.example.langio.useful.Hint
import java.util.Locale

@Composable
fun ExamTranslate (modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { HeaderBar(modifier, showLevel = true, showExam = true) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
//            ExamCard("???", painterResource(R.drawable.cow))
            GameController.instance.currentScreenWordsToBeUsed?.get(0)
                ?.let { ExamCard(it.englishWord, painterResource(R.drawable.cow)) }
            InputBox(modifier)
            Spacer(modifier = Modifier.height(20.dp)) // Add some spacing
            BackToLevelMenuButton() // Add the button here
//            Hint()
        }
    }
}




@Composable
fun InputBox(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf(TextFieldValue("")) } // Przechowywanie tekstu wpisanego przez użytkownika
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(modifier = modifier.padding(horizontal = 16.dp).fillMaxHeight(.7f)) {
        TextField(
            value = inputText,
            onValueChange = { newText -> inputText = newText },
            label = { Text("Wpisz tłumaczenie") },
            placeholder = { Text("Wpisz tutaj...") },
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
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Check",
                color = Color(0xFFF5F5F5),
            )
        }
    }
}

fun incorrectTranslation() {
    GameController.instance.decreaseLivesNumber()
}

fun correctTranslation() {
    GameController.instance.nextExamScreen(1)
}
