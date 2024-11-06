package com.example.langio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun MapScreen(navController: NavController, selectedTab: String = "map", modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { CustomBottomNavigationBar(navController, selectedTab) },
        topBar = { HeaderBar(modifier = modifier, showPfp = true, showLevel = false) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF403E3E))
        ) {

            // Display vertically aligned buttons with random X positions and connecting curves
            VerticalButtonsWithRandomXPositions()
        }
    }
}

@Composable
fun VerticalButtonsWithRandomXPositions() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val offsetMargin = 20
    val buttonSize = 50
    val randomXOffsets = remember {
        List(5) { Random.nextInt(offsetMargin, screenWidth - buttonSize).dp } // Generate random X offsets
    }

    // Draw connecting curved lines between buttons
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            randomXOffsets.windowed(2).forEachIndexed { index, (startX, endX) ->
                val startY = (offsetMargin + (index * 100)).dp.toPx() // Y position for the first button
                val endY = (offsetMargin + ((index + 1) * 100)).dp.toPx() // Y position for the next button

                // Create a path for the curve
                val path = Path().apply {
                    moveTo(startX.toPx(), startY)
                    val controlPoint = Offset((startX.toPx() + endX.toPx()) / 2, startY - 50)
                    quadraticTo(controlPoint.x, controlPoint.y, endX.toPx(), endY)
                }

                // Draw the curve
                drawPath(
                    path = path,
                    color = Color.Magenta,
                    style = Stroke(width = 4.dp.toPx())
                )
            }
        }

        // Place buttons at calculated positions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = offsetMargin.dp),
            verticalArrangement = Arrangement.spacedBy(100.dp) // Fixed vertical spacing between buttons
        ) {
            randomXOffsets.forEach { xOffset ->
                Button(
                    onClick = { /* Action on button click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff2c2c2c)),

                    modifier = Modifier
                        .size(buttonSize.dp)
                        .offset(x = xOffset) // Random horizontal offset for each button
                ) {
                    Text("★", color = Color(0xFF8E44AD), fontSize = 24.sp)
                }
            }
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
fun MapNode() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .background(Color.Black, shape = CircleShape)
    ) {
        Text("★", color = Color(0xFF8E44AD), fontSize = 24.sp)
    }
}
