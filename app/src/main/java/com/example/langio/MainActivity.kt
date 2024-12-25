package com.example.langio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langio.screens.DailyRewardScreen
import com.example.langio.screens.FlashcardScreen
import com.example.langio.screens.HomeScreen
import com.example.langio.screens.LevelMenuScreen
import com.example.langio.screens.LoginScreen
import com.example.langio.screens.MapScreen
import com.example.langio.screens.ProfileScreen
import com.example.langio.screens.RegisterScreen
import com.example.langio.ui.theme.LANGIOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LANGIOTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "flashcard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("register") { RegisterScreen() }
            composable("dailyReward") { DailyRewardScreen(navController) }
            composable("map") { MapScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("levelMenu") { LevelMenuScreen(navController) }
            composable("flashcard") { FlashcardScreen(navController) }




//            composable("test") { test(navController) }
        }
    }
}
