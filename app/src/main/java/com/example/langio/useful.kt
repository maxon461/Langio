package com.example.langio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun CustomBottomNavigationBar(navController: NavController, selectedTab: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE7F6))
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Daily Rewards Tab
        CustomNavItem(
            label = "Daily rewards",
            imageResId = R.drawable.cow,
            isSelected = selectedTab == "dailyReward",
            onClick = { navController.navigate("dailyReward") }
        )

        // Map Tab
        CustomNavItem(
            label = "Map",
            imageResId = R.drawable.cow,
            isSelected = selectedTab == "map",
            onClick = { navController.navigate("map") }
        )

        // Profile Tab
        CustomNavItem(
            label = "Profile",
            imageResId = R.drawable.cow,
            isSelected = selectedTab == "profile",
            onClick = { navController.navigate("profile") }
        )
    }
}

@Composable
fun CustomNavItem(
    label: String,
    imageResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = label,
            modifier = Modifier.size(20.dp) // Adjust size as needed
        )
        Text(
            text = label,
            fontSize = 10.sp, // Smaller text size for alignment
            color = if (isSelected) Color.Black else Color.Gray
        )
    }
}

