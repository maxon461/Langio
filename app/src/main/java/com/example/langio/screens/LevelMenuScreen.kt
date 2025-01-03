package com.example.langio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langio.useful.HeaderBar
import com.example.langio.R
import com.example.langio.controllers.GameController

@Composable
fun LevelMenuScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { HeaderBar(modifier, showPfp = false, showLevel = true, showExam = false) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
            LevelMenuGrid(modifier)
        }
    }
}

@Composable
fun LevelMenuGrid(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // First Menu Item
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp)
                .clickable {
                    GameController.instance.resetWordsToBeUsed()
                    GameController.instance.changeScreen(GameController.Screen.WORD_LIST)
                }
                .background(
                    Color(0xFF8559A5),
                    RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "List of all the words",
                    color = Color(0xFFFFE342),
                    fontSize = 44.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(lineHeight = 48.sp),
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.list),
                    contentDescription = "List Icon",
                    tint = Color.LightGray,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Second Menu Item
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp)
                .clickable {
                    GameController.instance.resetWordsToBeUsed()
                    GameController.instance.changeScreen(GameController.Screen.FLASHCARD)
                }
                .background(
                    Color(0xFF8559A5),
                    RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Show flashcards",
                    color = Color(0xFFFFE342),
                    fontSize = 44.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(lineHeight = 48.sp),
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.flashcard),
                    contentDescription = "Flashcard Icon",
                    tint = Color.LightGray,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Third Menu Item
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp)
                .clickable {
                    GameController.instance.resetWordsToBeUsed()
                    GameController.instance.changeScreen(GameController.Screen.EXAM_CHOICE)
                }
                .background(
                    Color(0xFF42033D),
                    RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "FINAL EXAM",
                    color = Color(0xFFFFE342),
                    fontSize = 44.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(lineHeight = 48.sp),
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.exam),
                    contentDescription = "Exam Icon",
                    tint = Color.LightGray,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        // Spacer to push the Back to Map Menu to the bottom
        Spacer(modifier = Modifier.weight(1f))

        // Back to Map Menu Item
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp)
                .clickable {
                    GameController.instance.changeScreen(GameController.Screen.MAP)
                }
                .background(
                    Color(0xFF2E3B55),
                    RoundedCornerShape(15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Back to Map",
                    color = Color(0xFFFFE342),
                    fontSize = 44.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(lineHeight = 48.sp),
                    fontWeight = FontWeight.Bold
                )

            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}




