package com.example.langio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun CustomBottomNavigationBar(navController: NavController, selectedTab: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = label,
            modifier = modifier.size(20.dp) // Adjust size as needed
        )
        Text(
            text = label,
            fontSize = 10.sp, // Smaller text size for alignment
            color = if (isSelected) Color.Black else Color.Gray
        )
    }
}



@Composable
fun HeaderBar(modifier: Modifier = Modifier, showPfp: Boolean, showLevel: Boolean) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showPfp) {
            Image(
                painter = painterResource(id = R.drawable.cow), // Zamień na rzeczywisty zasób obrazu
                contentDescription = "Avatar",
                modifier = modifier.size(80.dp)
            )
        }
        if (showLevel) {
            Text(
                text = "lvl 1",
                fontSize = 40.sp,
                color = Color(0xFFFFE342),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.cow), // Zamień na rzeczywisty zasób ikony
                contentDescription = "Star Icon",
                tint = Color.LightGray,
                modifier = modifier.size(60.dp)
            )
            Text(text = "21", fontSize = 40.sp, color = Color.LightGray)
        }
    }
}
