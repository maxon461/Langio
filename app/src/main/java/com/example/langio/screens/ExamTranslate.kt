package com.example.langio.screens

import android.view.KeyEvent
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
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.langio.R
import com.example.langio.useful.BackToLevelMenuButton
import com.example.langio.useful.ExamCard
import com.example.langio.useful.HeaderBar
import com.example.langio.useful.Hint

@Composable
fun ExamTranslate (modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { HeaderBar(modifier, showPfp = false, showLevel = true, showExam = true) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
//            ExamCard("???", painterResource(R.drawable.cow))
            ExamCard("ginger", painterResource(R.drawable.cow))
            InputBox(modifier)
            Spacer(modifier = Modifier.height(20.dp)) // Add some spacing
            BackToLevelMenuButton() // Add the button here
            Hint()
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
                imeAction = ImeAction.Done // Ustawienie akcji na "Done" (Enter)
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide() // Zamknięcie klawiatury
                    println("Wpisane tłumaczenie: ${inputText.text}") // Działanie po wciśnięciu Enter
                }
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8559A5)),
            onClick = {
                // Obsługa akcji po kliknięciu, np. weryfikacja odpowiedzi
                println("Wpisane tłumaczenie: ${inputText.text}")
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(

                text = "Sprawdź",
                color = Color(0xFFF5F5F5),
            )
        }
    }
}