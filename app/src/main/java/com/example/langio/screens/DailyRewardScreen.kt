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
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate

@Composable
fun DailyRewardScreen(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { CustomBottomNavigationBar(selectedTab = "dailyReward") },
        topBar = { HeaderBar(modifier, showPfp = true) }
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
    val context = LocalContext.current // Get the current context
    val showDialog = remember { mutableStateOf(false) }
    val unlockedDays = GameController.instance.getUnlockedDays(context)
    val userData = GameController.instance.userData

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        val today = LocalDate.now().dayOfMonth // Get today's date
        val days = (1..16).toList() // Assuming the rewards are for 16 days

        for (row in days.chunked(4)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { day ->
                    RewardItem(
                        day = day,
                        currentStreak = unlockedDays,
                        isDailyRewardTaken = GameController.instance.isDailyRewardTaken, // Today's reward is unavailable if taken
                        onClick = {
                            println("KLIKNIETO ALE JESZCZE PRZED IFEM")
                            println("day = $day")
                            println("unlockedDays = $unlockedDays")
                            println("isDailyRewardTaken = ${GameController.instance.isDailyRewardTaken}")
                            if (day == unlockedDays && !GameController.instance.isDailyRewardTaken) {
                                println("KLIKNIETO ALE JUŻ W IFEM")
                                GameController.instance.collectReward(context, day)
                                showDialog.value = true
                            }
                        }
                    )
                }
            }
            Spacer(modifier = modifier.height(8.dp))
        }
    }

    if (showDialog.value) {
        RewardDialog(
            day = unlockedDays,
            onDismiss = { showDialog.value = false }
        )
    }
}









@Composable
fun RewardItem(day: Int, currentStreak: Int, isDailyRewardTaken: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val isAvailable = (day == currentStreak && !isDailyRewardTaken)

    Box(modifier = modifier.padding(8.dp)) {
        Column(
            modifier = modifier
                .background(
                    if (isAvailable)
                        Color(0xFFD2BE13)
                    else
                        if (currentStreak >= day)
                            Color(0xFF8559A5)
                        else
                            Color.Gray, // Gray out unavailable rewards
                    RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .size(60.dp)
                .let {
                    if (isAvailable) {
                        it.clickable { onClick() } // Clickable only if available
                    } else it
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Day $day",
                color = if (isAvailable) Color.White else Color.LightGray, // Update text color
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




