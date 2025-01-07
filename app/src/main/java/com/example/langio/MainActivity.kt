package com.example.langio

import android.content.pm.ActivityInfo
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
import com.example.langio.controllers.DataController
import com.example.langio.controllers.GameController
import com.example.langio.ui.theme.LANGIOTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appStartTime = System.currentTimeMillis()

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        enableEdgeToEdge()
        setContent {
            val fileName = "words"
            DataController.readWordsFromFile(this@MainActivity, fileName)

            LANGIOTheme {
                MainScreen()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val userData = GameController.instance.getActualUserData(getMinutesPassed())

        if (userData != null) {
            GameController.instance.saveUserData(this, userData)
        }

    }

    companion object {
        var appStartTime: Long = 0

        fun getMinutesPassed(): Long {
            return (System.currentTimeMillis() - appStartTime) / (60 * 1000)
        }
    }





}

@Composable
fun MainScreen() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->
        GameController.instance.SetupNavigation(
            modifier = Modifier,
            innerPadding = Modifier.padding(innerPadding)
        )
    }
}


