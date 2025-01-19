import androidx.compose.runtime.Composable
import com.example.langio.controllers.GameController
import com.example.langio.models.UserData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.math.min

class GameControllerTest {

    private lateinit var gameController: GameController
    private lateinit var userData: UserData

    @Before
    fun setup() {
        gameController = GameController.instance
        userData = UserData("TEST_NAME",
                            joinDate = LocalDate.now().toString(),
                            36,
                            unlockedLevel = 4,
                            minutesSpent = 70,
                            hintsRemaining = 13,
                            dailyRewardStreak = 10,
                            isDailyRewardTaken = true,
                            lastCollectedDate = LocalDate.now().toString())

        gameController.userData = userData
    }

    @Test
    fun `test increaseHintNumber`() {
        val initialHints = gameController.hintNumber
        gameController.increaseHintNumber(2)
        assertEquals(initialHints + 2, gameController.hintNumber)
    }

    @Test
    fun `test decreaseHintNumber`() {
        gameController.hintNumber = 5
        gameController.decreaseHintNumber()
        assertEquals(4, gameController.hintNumber)
    }

    @Test
    fun `test decreaseLivesNumber when lives remain`() {
        val initialLives = gameController.livesNumber
        gameController.decreaseLivesNumber()
        assertEquals(initialLives - 1, gameController.livesNumber)
    }

    @Test
    fun `test decreaseLivesNumber when no lives remain`() {
        gameController.livesNumber = 0
        gameController.decreaseLivesNumber()
        assertEquals(0, gameController.livesNumber)
    }

    @Test
    fun `test resetVariablesForExam`() {
        gameController.resetVariablesForExam()
        assertEquals(3, gameController.livesNumber)
        assertEquals(4, gameController.examChoicesScreenLeft)
        assertEquals(2, gameController.examTranslateScreenLeft)
        assertEquals(1, gameController.examConnectScreenLeft)
    }



    @Test
    fun `test getActualUserData`() {
        gameController.username = "test_user"
        gameController.unlockedLevelId = 5
        gameController.learnedWords = 50
        gameController.hintNumber = 3
        gameController.dailyRewardStreak = 7
        gameController.isDailyRewardTaken = true

        val minutesPassed = 120L
        val userData = gameController.getActualUserData(minutesPassed)

        assertEquals("test_user", userData?.username)
        assertEquals(5, userData?.unlockedLevel)
        assertEquals(50, userData?.learnedWords)
        assertEquals(3, userData?.hintsRemaining)
        assertEquals(7, userData?.dailyRewardStreak)
        assertEquals(true, userData?.isDailyRewardTaken)
    }



    @Test
    fun `test addHints when reward is collected`() {
        val day = 5
        val gameController = GameController.instance
        gameController.hintNumber = 0

        gameController.collectRewards(day)

        val expectedHints = ((day - 1) / 4) + 1
        assertEquals(expectedHints, gameController.hintNumber)
        assertTrue(gameController.isDailyRewardTaken)
    }

    @Test
    fun `should unlock next level without increasing hints level if not multiple of 5`() {
        val gameController = GameController.instance
        gameController.unlockedLevelId = 4
        gameController.hintNumber = 0
        gameController.currentLevelId = 4

        gameController.onExamPassed()

        assertEquals(5, gameController.unlockedLevelId)
        assertEquals(0, gameController.hintNumber)
    }

    @Test
    fun `should unlock next level without increasing hints level if multiple of 5`() {
        val gameController = GameController.instance
        gameController.unlockedLevelId = 5
        gameController.hintNumber = 0
        gameController.currentLevelId = 5

        gameController.onExamPassed()

        assertEquals(6, gameController.unlockedLevelId)
        assertEquals(1, gameController.hintNumber)
    }


    class TestContext : android.content.ContextWrapper(null)
}

