package com.example.langio.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langio.useful.HeaderBar
import com.example.langio.R

@Composable
fun LevelMenuScreen(navController: NavController, modifier: Modifier = Modifier) {
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
        Spacer(modifier = Modifier.height(20.dp)) // Odstęp między pierwszym i drugim elementem

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp)
                .background(Color(0xFF8559A5),
            RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text("List of all the words", color = Color(0xFFFFE342), fontSize = 24.sp)
                Icon(
                    painter = painterResource(id = R.drawable.cow), // Zamień na rzeczywisty zasób ikony
                    contentDescription = "List Icon",
                    tint = Color.LightGray,
                    modifier = modifier.size(60.dp)
                )            }

        }

        Spacer(modifier = Modifier.height(20.dp)) // Odstęp między pierwszym i drugim elementem

        // Drugi element menu w górnej części
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp)
                .background(Color(0xFF8559A5),
            RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text("Show flashcards", color = Color(0xFFFFE342), fontSize = 24.sp)
                Icon(
                    painter = painterResource(id = R.drawable.cow), // Zamień na rzeczywisty zasób ikony
                    contentDescription = "Flashcard Icon",
                    tint = Color.LightGray,
                    modifier = modifier.size(60.dp)
                )            }
        }

        // Dynamiczny odstęp, który wypełnia całą przestrzeń między górną a dolną częścią
        Spacer(modifier = Modifier.weight(1f))

        // Trzeci element menu w dolnej części
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(20.dp)
                .background(Color(0xFF42033D),
            RoundedCornerShape(15.dp))
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text("FINAL EXAM", color = Color(0xFFFFE342), fontSize = 24.sp)
                Icon(
                    painter = painterResource(id = R.drawable.cow), // Zamień na rzeczywisty zasób ikony
                        contentDescription = "Exam Icon",
                    tint = Color.LightGray,
                    modifier = modifier.size(60.dp)
                )            }
        }
        Spacer(modifier = Modifier.height(20.dp)) // Odstęp między pierwszym i drugim elementem

    }
}
