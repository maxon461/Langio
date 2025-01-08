package com.example.langio.controllers

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.langio.models.UserData
import com.example.langio.models.WordInstance
import com.example.langio.screens.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.properties.Delegates

const val BASIC_LIVES_NUMBER = 3
const val NUMBER_OF_WORDS_PER_LEVEL = 10
const val NUMBER_OF_CHOICE_SCREENS_PER_EXAM = 4
const val NUMBER_OF_TRANSLATE_SCREENS_PER_EXAM = 2
const val NUMBER_OF_CONNECT_SCREENS_PER_EXAM = 1
val TEST_DATE_NOW = LocalDate.of(2025, 1, 13)//LocalDate.now()//LocalDate.of(2025, 1, 13)

const val USER_DATA_FILE = ".\\user_datdahgfsa11.json"



class GameController {



    private lateinit var navController: NavHostController
    var userData by mutableStateOf<UserData?>(null)
        private set
    var username: String = "TEMP"
    var learnedWords by Delegates.notNull<Int>()
        private set
    //    var unlockedLevel by Delegates.notNull<Int>()
//        private set
    var dailyRewardStreak by Delegates.notNull<Int>()
        private set
    var isDailyRewardTaken: Boolean = false
    var lastCollectedDate: LocalDate = LocalDate.of(2000, 12, 11)


    var unlockedLevelId by mutableIntStateOf(1)

    var currentLevelId: Int? = null
        private set


    var livesNumber by mutableIntStateOf(0)
        private set
    var hintNumber by mutableIntStateOf(0)
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
        REWARDS("dailyReward"),
        MAP("map"),
        PROFILE("profile"),
        WORD_LIST("wordList"),
        FLASHCARD("flashcard"),
        EXAM_CHOICE("examChoice"),
        EXAM_TRANSLATE("examTranslate"),
        EXAM_CONNECT_WORDS("connectWords"),
        EXAM_SUMMARY("examSummary"),
        LEVEL_MENU("levelMenu")
    }

    enum class AnswerState {
        IDLE,
        CORRECT,
        INCORRECT
    }

    @Composable
    fun SetupNavigation(modifier: Modifier = Modifier, innerPadding: Modifier = Modifier) {
        this.navController = rememberNavController()
        val context = LocalContext.current
        userData = loadUserData(context)
        hintNumber = userData?.hintsRemaining ?: -1
        username = userData?.username ?: "MICHAŁ"
        learnedWords = userData?.learnedWords ?: -1
//        unlockedLevel = userData?.unlockedLevel ?: -1
        unlockedLevelId = userData?.unlockedLevel ?: -1
        dailyRewardStreak = userData?.dailyRewardStreak ?: -1
        isDailyRewardTaken = userData?.isDailyRewardTaken ?: false
        lastCollectedDate = if (userData?.lastCollectedDate != null) {
            LocalDate.parse(userData!!.lastCollectedDate)
        } else {
            LocalDate.of(1000, 12, 22)
        }

        Log.e("DAYS DIFF", ChronoUnit.DAYS.between(lastCollectedDate, TEST_DATE_NOW/*LocalDate.now()*/).toInt().toString())

        if (ChronoUnit.DAYS.between(lastCollectedDate, TEST_DATE_NOW/*LocalDate.now()*/).toInt() == 1 && isDailyRewardTaken) {
            dailyRewardStreak++
            isDailyRewardTaken = false
            Log.e("TEST", "WELCOME BACK HERE'S ANOTHER REWARD")
        }
        else if (ChronoUnit.DAYS.between(lastCollectedDate, TEST_DATE_NOW/*LocalDate.now()*/).toInt() == 2) {
            dailyRewardStreak = 1
            isDailyRewardTaken = false
            Log.e("TEST", "YOU SHOULD VISIT US MORE OFTEN")
        }

        if (dailyRewardStreak > 16) {
            dailyRewardStreak = 1
        }

        println("AFTER CHANGES")
        println(isDailyRewardTaken)
        println(dailyRewardStreak)

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

    fun loadUserData(context: Context): UserData? {
        return UserData.loadFromJson(context, USER_DATA_FILE)
    }

    fun saveUserData(context: Context, userData: UserData) {
//        UserData.
        UserData.saveToJson(context, USER_DATA_FILE, userData)
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

    fun increaseHintNumber(hintNumberToAdd: Int = 1) {
        hintNumber = hintNumber + hintNumberToAdd
        println("HintNumber increased: $hintNumber")
    }

    fun decreaseHintNumber() {
        hintNumber--
        println("HintNumber decreased: $hintNumber")
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

    fun getActualUserData(minutesPassed: Long): UserData? {
        val newUserData = userData?.let {
            UserData(
                username = username,
                joinDate = it.joinDate,
                learnedWords = learnedWords,
                unlockedLevel = unlockedLevelId,
                minutesSpent = it.minutesSpent + minutesPassed,
                hintsRemaining = hintNumber,
                dailyRewardStreak = dailyRewardStreak,
                isDailyRewardTaken = isDailyRewardTaken,
                lastCollectedDate = lastCollectedDate.toString()
            )
        }
        return newUserData
    }

    fun getMinutesOfThisSession(): Int {
//        TODO()
        return 15
    }


    fun onExamPassed() {
        currentLevelId?.let {
            if (it == unlockedLevelId) {
                if (unlockedLevelId % 5 == 0)
                {
                    hintNumber++
                }
                unlockedLevelId++
                println("Next level unlocked: $unlockedLevelId")
            }
            changeScreen(Screen.MAP)
        }
    }

    fun onExamFailed() {
        println("Exam failed. No level unlocked.")
        changeScreen(Screen.LEVEL_MENU)
    }

    fun collectReward(context: Context, day: Int) {
        lastCollectedDate = TEST_DATE_NOW//LocalDate.now()
        increaseHintNumber(((day-1)/4).toInt() + 1)
        isDailyRewardTaken = true
        println("REWARDTAKEN")
    }


    fun getUnlockedDays(context: Context): Int {
//        // Reset streak if not consecutive
//        if (lastCollectedDate == null || lastCollectedDate.plusDays(1) != today) {
//            resetStreak(context)
//        }

        return dailyRewardStreak
    }

    private fun resetStreak(context: Context) {
        userData?.apply {
            dailyRewardStreak = 1
            isDailyRewardTaken = false
        }
        saveUserData(context, userData!!)
        println("Streak reset to 1.")
    }

//    fun updateStreak(context: Context) {
//        val today = LocalDate.now()
//        val lastCollectedDate = userData?.lastCollectedDate?.let { LocalDate.parse(it) }
//
//        // Update the streak if today is consecutive with the last collected date
//        if (lastCollectedDate == null || lastCollectedDate.plusDays(1) == today) {
//            userData?.dailyRewardStreak = (userData?.dailyRewardStreak ?: 1) + 1
//        } else {
//            resetStreak(context)
//        }
//        userData?.lastCollectedDate = today.toString()
//        saveUserData(context, userData!!)
//    }




    companion object {
        val instance: GameController by lazy { GameController() }
    }
}