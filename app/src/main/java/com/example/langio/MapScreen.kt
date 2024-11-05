package com.example.langio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

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



@Composable
fun MapScreen(navController: NavController, selectedTab: String = "map") {
    Scaffold(
        bottomBar = { CustomBottomNavigationBar(navController, selectedTab) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF403E3E)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            MapPathSection()
        }
    }
}

@Composable
fun MapPathSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(5) {
            MapNode()
            if (it < 4) {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}



@Composable
//level: Int, onClick: () -> Unit
fun MapNode() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .background(Color.Black, shape = CircleShape)

    ) {
        Text("★", color = Color(0xFF8E44AD), fontSize = 24.sp) // Display level number with star
    }
}
