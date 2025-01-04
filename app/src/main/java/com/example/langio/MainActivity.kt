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
import com.example.langio.controllers.DataController
import com.example.langio.controllers.GameController
import com.example.langio.ui.theme.LANGIOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val fileName = "words"
            DataController.readWordsFromFile(this@MainActivity, fileName)

            LANGIOTheme {
                MainScreen()
            }
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
