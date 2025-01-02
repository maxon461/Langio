package com.example.langio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.langio.R
import com.example.langio.controllers.GameController
import com.example.langio.ui.theme.JacquesFrancoisShadow
import com.example.langio.ui.theme.LANGIOTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF403E3E)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LANGIO",
            fontSize = 48.sp,
            color = Color(0xFF8559A5),
            fontWeight = FontWeight.Bold,
            fontFamily = JacquesFrancoisShadow,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.cow), // Dodaj obrazek krowy do drawable
            contentDescription = "Cow Icon",
            modifier = Modifier
                .padding(vertical = 38.dp)
                .size(150.dp)
                .padding(bottom = 32.dp)
        )

        Button(
            onClick = { GameController.instance.changeScreen(GameController.Screen.LOGIN) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8559A5)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(200.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "I have an account", color = Color(0xFFFFE342))
                Text(text = "tengo una cuenta", color = Color(0xFFFFE342), fontSize = 12.sp)
            }
        }

        Button(
            onClick = { GameController.instance.changeScreen(GameController.Screen.REGISTER) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE858AE)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(vertical = 38.dp)
                .width(200.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Get started", color = Color(0xFF430512))
                Text(text = "Empezar", color = Color(0xFF430512), fontSize = 12.sp)
            }
        }
    }
}

