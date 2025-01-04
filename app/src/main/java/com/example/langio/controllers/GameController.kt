package com.example.langio.controllers

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langio.models.Level
import com.example.langio.models.WordInstance
import com.example.langio.screens.*
class GameController {

    private lateinit var navController: NavHostController

    var currentLevelId: Int? = null
        private set

    private val _collectedRewards = mutableStateListOf<Int>()
    val collectedRewards: List<Int> get() = _collectedRewards

    fun collectReward(day: Int) {
        if (!_collectedRewards.contains(day)) {
            _collectedRewards.add(day) // Add the reward to the collected list
            increaseHintScore() // Increment hint score
        }
    }

    var hintScore by mutableStateOf(0)
        private set

    var currentLevelWords: List<WordInstance>? = null
    var currentScreenWordsToBeUsed: MutableList<WordInstance>? = null

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
        LEVEL_MENU("levelMenu") // Static route since global variable is used
    }

    @Composable
    fun SetupNavigation(modifier: Modifier = Modifier, innerPadding: Modifier = Modifier) {
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
            composable(Screen.LEVEL_MENU.route) {
                LevelMenuScreen()
            }
        }
    }

    fun changeScreen(nextScreen: Screen) {
        try {
            val route = nextScreen.route
            navController.navigate(route)
        } catch (e: Exception) {
            println("Navigation failed: ${e.message}")
        }
    }

    fun setCurrentLevel(levelId: Int) {
        currentLevelId = levelId
    }

    fun prepareDataForCurrentLevel()
    {
        currentLevelWords = currentLevelId?.let { DataController.getWordsForLevel(it) }
        currentScreenWordsToBeUsed = currentLevelWords?.toMutableList()
    }

    fun resetWordsToBeUsed()
    {
        currentScreenWordsToBeUsed = currentLevelWords?.toMutableList()
    }

    fun increaseHintScore() {
        hintScore++
        println("HintScore increased: $hintScore")
    }


    //TEST



    companion object {
        val instance: GameController by lazy { GameController() }
    }
}
