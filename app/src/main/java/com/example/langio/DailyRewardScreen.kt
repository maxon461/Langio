package com.example.langio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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

@Composable
fun DailyRewardScreen(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF403E3E))
            .padding(16.dp)
    ) {
        HeaderSection()
        BannerText()
        RewardsGrid()
        CustomBottomNavigationBar(navController, "daily_rewards")
    }

}

@Composable
fun HeaderSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar Image on the left
        Image(
            painter = painterResource(id = R.drawable.cow), // Replace with actual avatar image resource
            contentDescription = "Avatar",
            modifier = modifier.size(80.dp)
        )

        // Star icon and score on the right
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.cow), // Replace with actual star icon resource
                contentDescription = "Star Icon",
                tint = Color.LightGray,
                modifier = modifier.size(60.dp)
            )
            Text(text = "21", fontSize = 40.sp, color = Color.LightGray)
        }
    }
}

@Composable
fun BannerText(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(vertical = 12.dp))
    {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFF8559A5), RoundedCornerShape(8.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your daily rewards!\n" +
                        "Visit us tomorrow to get more stars!",
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
        // Displaying a 5x4 grid for 20 days
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
    Box(modifier = modifier.padding(8.dp))
    {
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
                painter = painterResource(id = R.drawable.chest), // Replace with actual chest image resource
                contentDescription = "Chest",
                modifier = modifier.size(40.dp)
            )
        }
    }
}




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
            imageResId = R.drawable.cow, // Replace with your actual image resource
            isSelected = selectedTab == "daily_rewards",
            onClick = { navController.navigate("daily_rewards") }
        )

        // Map Tab
        CustomNavItem(
            label = "Map",
            imageResId = R.drawable.cow, // Replace with your actual image resource
            isSelected = selectedTab == "map",
            onClick = { navController.navigate("map") }
        )

        // Profile Tab
        CustomNavItem(
            label = "Profile",
            imageResId = R.drawable.cow, // Replace with your actual image resource
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

@Preview(showBackground = true)
@Composable
fun DailyRewardScreenPreview() {
    DailyRewardScreen(navController = rememberNavController())
}
