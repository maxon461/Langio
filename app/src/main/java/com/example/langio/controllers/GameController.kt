package com.example.langio.controllers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langio.models.WordInstance
import com.example.langio.screens.*
import kotlin.random.Random

const val BASIC_LIVES_NUMBER = 3
const val NUMBER_OF_WORDS_PER_LEVEL = 10
const val NUMBER_OF_CHOICE_SCREENS_PER_EXAM = 1
const val NUMBER_OF_TRANSLATE_SCREENS_PER_EXAM = 1
const val NUMBER_OF_CONNECT_SCREENS_PER_EXAM = 1



class GameController {

    private lateinit var navController: NavHostController

    var currentLevelId: Int? = null
        private set

    private val _collectedRewards = mutableStateListOf<Int>()
    val collectedRewards: List<Int> get() = _collectedRewards

    fun collectReward(day: Int) {
        if (!_collectedRewards.contains(day)) {
            _collectedRewards.add(day)
            increaseHintNumber()
        }
    }

    var hintNumber by mutableIntStateOf(0)
        private set

    var livesNumber by mutableIntStateOf(0)
        private set

    var examChoicesScreenLeft by mutableIntStateOf(0)
        private set
    var examTranslateScreenLeft by mutableIntStateOf(0)
        private set
    var examConnectScreenLeft by mutableIntStateOf(0)
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
        EXAM_CONNECT_WORDS("connectWords"),
        EXAM_SUMMARY("examSummary"),
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
            composable(Screen.EXAM_CONNECT_WORDS.route) { ExamConnectWordsScreen() }
            composable(Screen.EXAM_SUMMARY.route) { ExamSummaryScreen() }
            composable(Screen.LEVEL_MENU.route) { LevelMenuScreen() }
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

    fun increaseHintNumber() {
        hintNumber++
        println("HintNumber increased: $hintNumber")
    }

    fun decreaseLivesNumber() {
        livesNumber--
        if (livesNumber < 0) {
            goToExamSummary()
        }
        println("LivesNumber decreased: $livesNumber")
    }

    fun resetVariablesForExam() {
        livesNumber = BASIC_LIVES_NUMBER
        examChoicesScreenLeft = NUMBER_OF_CHOICE_SCREENS_PER_EXAM
        examTranslateScreenLeft = NUMBER_OF_TRANSLATE_SCREENS_PER_EXAM
        examConnectScreenLeft = NUMBER_OF_CONNECT_SCREENS_PER_EXAM
    }


    fun startExam() {
        resetVariablesForExam()
        currentScreenWordsToBeUsed?.shuffle()
        nextExamScreen(0)
    }

    fun nextExamScreen(usedWordsNumber: Int) {
        currentScreenWordsToBeUsed?.subList(0, usedWordsNumber)?.clear()
        val nextScreen = chooseNextExamScreenAndDecreaseItsAvailability()

        if (nextScreen == Screen.EXAM_CHOICE) {
            examChoicesScreenLeft--
        }

        else if (nextScreen == Screen.EXAM_TRANSLATE)
            examTranslateScreenLeft--
        else if (nextScreen == Screen.EXAM_CONNECT_WORDS)
            examConnectScreenLeft--
        else if (nextScreen == null)
            goToExamSummary()

        if (nextScreen != null) {
            changeScreen(nextScreen)
        }


    }

    private fun goToExamSummary() {
        changeScreen(Screen.EXAM_SUMMARY)
    }

    private fun chooseNextExamScreenAndDecreaseItsAvailability(): Screen? {
        val availableScreen: MutableList<Screen> = mutableListOf()
        if (examChoicesScreenLeft > 0)
            availableScreen.add(Screen.EXAM_CHOICE)
        if (examTranslateScreenLeft > 0)
            availableScreen.add(Screen.EXAM_TRANSLATE)
        if (examConnectScreenLeft > 0)
            availableScreen.add(Screen.EXAM_CONNECT_WORDS)

        try {
            val nextScreen = availableScreen.random()
            availableScreen.remove(nextScreen)
            return nextScreen
        }
        catch (e: NoSuchElementException)
        {
            return null
        }
    }


    //TEST



    companion object {
        val instance: GameController by lazy { GameController() }
    }
}
