package com.example.langio.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.langio.R
import com.example.langio.controllers.GameController
import com.example.langio.useful.HeaderBar


//@Composable
//fun ExamSummaryScreen (modifier: Modifier = Modifier) {
//    Scaffold(
//        topBar = { HeaderBar(modifier, showLevel = true, showExam = true) }
//    ) { paddingValues ->
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .background(Color(0xFF403E3E))
//                .padding(paddingValues)
//        ) {
//            if (GameController.instance.livesNumber >= 0) {
//                //WYGRANA
//
//            }
//            else {
//                //PRZEGRANA
//            }
//        }
//
//    }
//}
//


@Composable
fun ExamSummaryScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            HeaderBar(modifier, showLevel = true, showExam = true)
        }
    ) { paddingValues ->
        val isExamPassed = GameController.instance.livesNumber >= 0

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(if (isExamPassed) Color(0xFF4CAF50) else Color(0xFFF44336)) // Zielone tło dla zdanych, czerwone dla niezdanych
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Obraz w zależności od wyniku
            Image(
                painter = painterResource(
                    if (isExamPassed) R.drawable.thumb_up_img else R.drawable.thumb_down_img
                ),
                contentDescription = if (isExamPassed) "Success" else "Failure",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp)
            )

            // Tekst z wynikiem egzaminu
            Text(
                text = if (isExamPassed) "Congratulations! You passed the exam!" else "Sorry, you failed the exam.",
                color = Color.White,
//                style = MaterialTheme.typography,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Przycisk, który pozwala wrócić lub spróbować ponownie
            Button(
                onClick = {
                    GameController.instance.changeScreen(GameController.Screen.LEVEL_MENU)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExamPassed) Color(0xFF2E7D32) else Color(0xFFD32F2F)
                ),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "Continue",
                    color = Color.White
                )
            }
        }
    }
}

