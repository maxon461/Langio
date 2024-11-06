package com.example.langio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.Scaffold

@Composable
fun DailyRewardScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { CustomBottomNavigationBar(navController, selectedTab = "dailyReward") },
        topBar = { HeaderBar(modifier, showPfp = true, showLevel = false) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
            BannerText(modifier)
            RewardsGrid(modifier)
        }
    }
}



@Composable
fun BannerText(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(vertical = 12.dp)) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFF8559A5), RoundedCornerShape(8.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your daily rewards!\nVisit us tomorrow to get more stars!",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun RewardsGrid(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        val days = (1..16).toList()
        for (i in days.chunked(4)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                i.forEach { day ->
                    RewardItem(day)
                }
            }
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

@Composable
fun RewardItem(day: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(8.dp)) {
        Column(
            modifier = modifier
                .background(Color(0xFF8559A5), RoundedCornerShape(8.dp))
                .padding(8.dp)
                .size(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Day $day",
                color = Color.White,
                fontSize = 12.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.chest), // Zamień na rzeczywisty zasób obrazu
                contentDescription = "Chest",
                modifier = modifier.size(40.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DailyRewardScreenPreview() {
    DailyRewardScreen(navController = rememberNavController())
}
