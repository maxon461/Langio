package com.example.langio.controllers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langio.screens.*

class GameController {

    // NavController is now a member of the GameController class
    private lateinit var navController: NavHostController

    // Private variables
    private var numberOfHints: Int = 0
    private var currentLevel: Int = 1
    private var currentScreen: Screen = Screen.HOME

    enum class Screen(val route: String) {
        HOME("home"),
        LOGIN("login"),
        REGISTER("register"),
        REWARDS("dailyReward"),
        MAP("map"),
        PROFILE("profile"),
        WORD_LIST("wordList"),
        FLASHCARD("flashcard"),
        EXAM_CHOICE("examChoice"),
        EXAM_TRANSLATE("examTranslate"),
        CONNECT_WORDS("connectWords"),
        LEVEL_MENU("levelMenu/{levelId}") // Dynamic route for level menu
    }

    @Composable
    fun SetupNavigation(modifier: Modifier = Modifier, innerPadding: Modifier = Modifier) {
        // Initialize the NavController
        this.navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.HOME.route,
            modifier = modifier.then(innerPadding)
        ) {
            addRoutes(this)
        }
    }

    private fun addRoutes(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            composable(Screen.HOME.route) { HomeScreen() }
            composable(Screen.LOGIN.route) { LoginScreen() }
            composable(Screen.REGISTER.route) { RegisterScreen() }
            composable(Screen.REWARDS.route) { DailyRewardScreen() }
            composable(Screen.MAP.route) { MapScreen() }
            composable(Screen.PROFILE.route) { ProfileScreen() }
            composable(Screen.WORD_LIST.route) { WordListScreen() }
            composable(Screen.FLASHCARD.route) { FlashcardScreen() }
            composable(Screen.EXAM_CHOICE.route) { ExamChoice() }
            composable(Screen.EXAM_TRANSLATE.route) { ExamTranslate() }
            composable(Screen.CONNECT_WORDS.route) { ConnectWordsScreen() }
            composable(Screen.LEVEL_MENU.route) { backStackEntry ->
                val levelId = backStackEntry.arguments?.getString("levelId")?.toIntOrNull()
                LevelMenuScreen(levelId = levelId)
            }
        }
    }

    fun changeScreen(nextScreen: Screen, params: Map<String, String> = emptyMap()) {
        try {
            val route = buildRoute(nextScreen, params)
            navController.navigate(route)
        } catch (e: Exception) {
            println("Navigation failed: ${e.message}")
        }
    }

    private fun buildRoute(screen: Screen, params: Map<String, String>): String {
        var route = screen.route
        params.forEach { (key, value) ->
            route = route.replace("{$key}", value)
        }
        return route
    }

    companion object {
        val instance: GameController by lazy { GameController() }
    }
}
