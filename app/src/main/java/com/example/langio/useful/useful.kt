package com.example.langio.useful

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
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
import com.example.langio.R


@Composable
fun CustomBottomNavigationBar(navController: NavController, selectedTab: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE7F6))
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceEvenly, // Używamy SpaceEvenly, żeby rozłożyć przyciski równomiernie
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Daily Rewards Tab
        CustomNavItem(
            label = "Daily rewards",
            imageResId = R.drawable.cow,
            isSelected = selectedTab == "dailyReward",
            onClick = { navController.navigate("dailyReward") },
            modifier = Modifier.weight(1f) // Ustawiamy wagę, aby przycisk wypełniał całą przestrzeń
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.Gray)
        )

        // Map Tab
        CustomNavItem(
            label = "Map",
            imageResId = R.drawable.cow,
            isSelected = selectedTab == "map",
            onClick = { navController.navigate("map") },
            modifier = Modifier.weight(1f) // Ustawiamy wagę, aby przycisk wypełniał całą przestrzeń
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.Gray)
        )

        // Profile Tab
        CustomNavItem(
            label = "Profile",
            imageResId = R.drawable.cow,
            isSelected = selectedTab == "profile",
            onClick = { navController.navigate("profile") },
            modifier = Modifier.weight(1f) // Ustawiamy wagę, aby przycisk wypełniał całą przestrzeń
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
            .fillMaxHeight() // Upewniamy się, że przycisk wypełnia całą dostępną wysokość
            .background(if (isSelected) Color(0xFFD8BFBF) else Color(0xFFEDE7F6))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Wyrównujemy zawartość w pionie
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = label,
            modifier = modifier.size(20.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
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

@Composable
fun ExamHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "lvl 1",
                fontSize = 40.sp,
                color = Color(0xFFFFE342),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "EXAM",
                fontSize = 40.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                tint = Color.LightGray,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "21",
                fontSize = 40.sp,
                color = Color.LightGray
            )
        }
    }
}
