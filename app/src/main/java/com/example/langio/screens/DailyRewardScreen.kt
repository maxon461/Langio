package com.example.langio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.langio.useful.CustomBottomNavigationBar
import com.example.langio.useful.HeaderBar
import com.example.langio.R
import com.example.langio.controllers.GameController

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

@Composable
fun DailyRewardScreen(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { CustomBottomNavigationBar(selectedTab = "dailyReward") },
        topBar = { HeaderBar(modifier, showPfp = true, showLevel = false, showExam = false) }
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
    val maxClickableRewards = 4 // Only the first 4 rewards are clickable
    val showDialog = remember { mutableStateOf(false) }
    val clickedDay = remember { mutableStateOf(0) } // Store the clicked day

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
                    RewardItem(
                        day,
                        isClickable = day <= maxClickableRewards && !GameController.instance.collectedRewards.contains(day),
                        onClick = {
                            clickedDay.value = day
                            showDialog.value = true
                            GameController.instance.collectReward(day) // Collect reward
                        }
                    )
                }
            }
            Spacer(modifier = modifier.height(8.dp))
        }
    }

    // Show dialog if the state is true
    if (showDialog.value) {
        RewardDialog(
            day = clickedDay.value,
            onDismiss = { showDialog.value = false }
        )
    }
}


@Composable
fun RewardItem(day: Int, isClickable: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(8.dp)) {
        Column(
            modifier = modifier
                .background(
                    if (isClickable) Color(0xFF8559A5) else Color.Gray, // Gray out collected rewards
                    RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .size(60.dp)
                .let {
                    if (isClickable) {
                        it.clickable { onClick() } // Pass the onClick function
                    } else it // Non-clickable modifier for collected or non-eligible rewards
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Day $day",
                color = if (isClickable) Color.White else Color.LightGray, // Update text color
                fontSize = 12.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.chest),
                contentDescription = "Chest",
                modifier = modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun RewardDialog(day: Int, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "OK")
            }
        },
        title = { Text(text = "Congratulations!") },
        text = { Text(text = "You have claimed the reward for Day $day!") }
    )
}




