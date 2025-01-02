package com.example.langio.controllers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.langio.screens.ConnectWordsScreen
import com.example.langio.screens.DailyRewardScreen
import com.example.langio.screens.ExamChoice
import com.example.langio.screens.ExamTranslate
import com.example.langio.screens.FlashcardScreen
import com.example.langio.screens.HomeScreen
import com.example.langio.screens.LevelMenuScreen
import com.example.langio.screens.LoginScreen
import com.example.langio.screens.ProfileScreen
import com.example.langio.screens.MapScreen
import com.example.langio.screens.RegisterScreen
import com.example.langio.screens.WordListScreen

class GameController {

    // NavController is now a member of the GameController class
    private lateinit var navController: NavHostController

    // Private variables
    private var numberOfHints: Int = 0
    private var currentLevel: Int = 1
    private var currentScreen: Screen = Screen.HOME

    enum class Screen {
        HOME,
        LOGIN,
        REGISTER,
        REWARDS,
        MAP,
        PROFILE,
        WORD_LIST,
        FLASHCARD,
        EXAM_CHOICE,
        EXAM_TRANSLATE,
        CONNECT_WORDS,
        LEVEL_MENU
    }


    @Composable
    fun SetupNavigation(modifier: Modifier = Modifier, innerPadding: Modifier = Modifier) {
        // Initialize the NavController
        this.navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = modifier.then(innerPadding)
        ) {
            addRoutes(this)
        }
    }



    private fun addRoutes(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            composable("home") { HomeScreen() }
            composable("login") { LoginScreen() }
            composable("register") { RegisterScreen() }
            composable("dailyReward") { DailyRewardScreen() }
            composable("map") { MapScreen() }
            composable("profile") { ProfileScreen() }
            composable("levelMenu") { LevelMenuScreen() }
            composable("flashcard") { FlashcardScreen() }
            composable("examChoice") { ExamChoice() }
            composable("connectWords") { ConnectWordsScreen() }
            composable("wordList") { WordListScreen() }
            composable("examTranslate") { ExamTranslate() }
        }
    }

    fun changeScreen(nextScreen: Screen) {
        when (nextScreen) {
            Screen.HOME -> navController.navigate("home")
            Screen.LOGIN -> navController.navigate("login")
            Screen.REGISTER -> navController.navigate("register")
            Screen.REWARDS -> navController.navigate("dailyReward")
            Screen.MAP -> navController.navigate("map")
            Screen.PROFILE -> navController.navigate("profile")
            Screen.WORD_LIST -> {

                navController.navigate("wordList")
            }
            Screen.FLASHCARD -> navController.navigate("flashcard")
            Screen.EXAM_CHOICE -> navController.navigate("examChoice")
            Screen.EXAM_TRANSLATE -> navController.navigate("examTranslate")
            Screen.CONNECT_WORDS -> navController.navigate("connectWords")
            Screen.LEVEL_MENU -> navController.navigate("levelMenu")
        }
    }



    companion object {
        val instance: GameController by lazy { GameController() }
    }

}



//TODO() ZASTANOW SIE NAD UZYCIEM OBJECT ZAMIAST CLASS