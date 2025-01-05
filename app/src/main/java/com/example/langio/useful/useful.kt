package com.example.langio.useful

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langio.R
import com.example.langio.controllers.GameController


@Composable
fun CustomBottomNavigationBar(selectedTab: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE7F6))
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceEvenly, // Używamy SpaceEvenly, żeby rozłożyć przyciski równomiernie
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomNavItem(
            label = "Daily rewards",
            imageResId = R.drawable.cow,
            isSelected = selectedTab == "dailyReward",
            onClick = { GameController.instance.changeScreen(GameController.Screen.REWARDS)},
            modifier = Modifier.weight(1f)
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
            onClick = { GameController.instance.changeScreen(GameController.Screen.MAP) },
            modifier = Modifier.weight(1f)
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
            onClick = { GameController.instance.changeScreen(GameController.Screen.PROFILE) },
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
fun BackToLevelMenuButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                try {
                    // Navigate to the level menu using GameController
                    GameController.instance.changeScreen(GameController.Screen.LEVEL_MENU)
                } catch (e: Exception) {
                    println("Navigation to Level Menu failed: ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(50.dp)
        ) {
            Text(
                text = "Back to Level Menu",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ExamCard (enWord: String, painter: Painter)
{

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .fillMaxHeight(0.8f)
            .height(400.dp),
        contentAlignment = Alignment.Center
    )
    {
        Card(
            modifier = Modifier
                .fillMaxSize(.9f),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray)
        )
        {
            Column(
                Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(25.dp)
                        .height(200.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row (
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                )
                {
                    Box (
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                    {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 40.sp,
                            color = Color.Black,
                            text = enWord)
                    }

                }

            }
        }
    }

}


class OneSidedHorizontalRoundedRectangle(isLeftRounded: Boolean) : Shape {
    var leftRounded = isLeftRounded
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            if (leftRounded) {
                moveTo(size.width, 0f)
                lineTo(size.height / 2, 0f)
                arcTo(
                    rect = androidx.compose.ui.geometry.Rect(
                        left = 0f,
                        top = 0f,
                        right = size.height,
                        bottom = size.height
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = -180f,
                    forceMoveTo = false
                )
                lineTo(size.width, size.height)
                close()
            } else {
                moveTo(0f, 0f)
                lineTo(size.width - size.height / 2, 0f)
                arcTo(
                    rect = androidx.compose.ui.geometry.Rect(
                        left = size.width - size.height,
                        top = 0f,
                        right = size.width,
                        bottom = size.height
                    ),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )
                lineTo(0f, size.height)
                close()
            }
        }
        return Outline.Generic(path)
    }
}


@Composable
fun HeaderBar(
    modifier: Modifier = Modifier,
    showPfp: Boolean = false,
    showLevel: Boolean = false,
    showExam: Boolean = false,
    showLives: Boolean = false
) {
    val hintNumber = GameController.instance.hintNumber
    val livesNumber = GameController.instance.livesNumber

    Row(
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showPfp) {
            Box(
                modifier = Modifier
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cow),
                    contentDescription = "Avatar",
                    modifier = modifier.size(62.dp)
                )
            }
        }
        if (showLevel) {
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = "lvl " + GameController.instance.currentLevelId,
                fontSize = 40.sp,
                color = Color(0xFFFFE342),
                fontWeight = FontWeight.Bold
            )
        }
        if(showExam) {
            Text(
//                modifier = Modifier.padding(horizontal = 20.dp),
                text = "E",
                fontSize = 40.sp,
                color = Color(0xFFDE3232),
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if(showLives) {
            Box(
                modifier = Modifier
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "Example Icon",
                    modifier = Modifier.size(46.dp)
                )

                Text(
                    text = livesNumber.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }



        Box(
            modifier = Modifier
                .size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "HintIcon",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(62.dp)
            )

            Text(
                text = hintNumber.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
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
                text = GameController.instance.hintNumber.toString(),
                fontSize = 40.sp,
                color = Color.LightGray
            )
        }
    }
}


@Composable
fun Hint(onClick: () -> Unit)
{
                Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF673AB7)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Hint",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hint",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
}