package com.example.langio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
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
import com.example.langio.useful.CustomBottomNavigationBar
import com.example.langio.useful.HeaderBar
import com.example.langio.R

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { CustomBottomNavigationBar(navController, selectedTab = "profile") },
        topBar = { HeaderBar(modifier, showPfp = false, showLevel = false) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
            ProfileBanner(modifier)
            StatisticsGrid(modifier)
        }
    }
}

@Composable
fun ProfileBanner(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(vertical = 12.dp)) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFF8559A5), RoundedCornerShape(8.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cow), // Zamień na rzeczywisty zasób obrazu
                    contentDescription = "Avatar",
                    modifier = modifier.size(80.dp),
                )
                Column(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Row()
                    {
                        Text(text = "YOUR USERNAME", fontSize = 20.sp, color = Color(0xffFFE342))
                        Image(
                            painter = painterResource(id = R.drawable.cow), // Zamień na rzeczywisty zasób obrazu
                            contentDescription = "Avatar",
                            modifier = modifier.size(15.dp)
                        )
                    }
                    Text(text = "JOINED: Aug 2022", fontSize = 15.sp, color = Color(0xffFFE342))

                }
            }

        }
    }
}

@Composable
fun StatisticsGrid(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        StatItem(modifier = modifier, "Learned words", 222)
        Spacer(modifier = modifier.height(8.dp))
        StatItem(modifier = modifier, "Actual level", 33)
        Spacer(modifier = modifier.height(8.dp))
        StatItem(modifier = modifier, "Minutes spent in app", 476)

    }
}




@Composable
fun StatItem(modifier: Modifier = Modifier, statName: String, statValue: Int) {
    Box(modifier = modifier.padding(8.dp).fillMaxWidth()) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = statName,
                color = Color(0xFF8559A5),
                fontWeight = FontWeight.SemiBold,
                fontSize = 35.sp,
            )
            Text(
                text = statValue.toString(),
                color = Color(0xffFFE342),
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
            )
        }
    }
}