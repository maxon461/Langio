package com.example.langio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langio.controllers.GameController
import com.example.langio.useful.HeaderBar

@Composable
fun WordListScreen(modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            HeaderBar(
                modifier = modifier,
                showPfp = false,
                showLevel = true,
                showExam = false,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
//                .background(Color(0xFF2C2C2C))
//                .background(Color(0xFF403E3E))

        ) {
            WordList()
            FinishButton()
        }
    }
}

@Composable
fun WordList()
{
    Surface(
        modifier = Modifier
//            .weight(1f)
            .fillMaxWidth()
            .padding(16.dp),
        color = Color(0xFFF8F8FF),
        shape = RoundedCornerShape(8.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(wordPairs) { pair ->
                WordPairItem(pair)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE0E0E0)
                )
            }
        }
    }
}

@Composable
private fun WordPairItem(pair: WordPair) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color(0xFFE6E6FA)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pair.firstLetter,
                    color = Color(0xFF4B0082),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${pair.spanish} - ${pair.english}",
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

@Composable
fun FinishButton()
{
    Button(
        onClick = {
            GameController.instance.changeScreen(GameController.Screen.LEVEL_MENU) // Update with your actual route
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF673AB7)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Finish",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

data class WordPair(
    val spanish: String,
    val english: String
) {
    val firstLetter: String get() = spanish.first().uppercase()
}

private val wordPairs = listOf(
    WordPair("descansar", "sleep"),
    WordPair("hablar", "speak"),
    WordPair("la mama", "mum"),
    WordPair("visitar", "visit"),
    WordPair("viajar", "ride"),
    WordPair("la zitron", "lemon")
)