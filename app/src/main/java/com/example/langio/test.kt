package com.example.langio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.langio.controllers.DataController
import com.example.langio.models.WordInstance

class MainActivity2 : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val words = mutableStateOf<List<WordInstance>>(emptyList())
            val fileName = "words"
            Column {
                DataController.readWordsFromFile(this@MainActivity2, fileName)

                val level1Words = DataController.getWordsForLevel(2) ?: emptyList()
                words.value = level1Words

                WordListDisplay(words = words.value)
            }
        }
    }
}

@Composable
fun WordListDisplay(words: List<WordInstance>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(words.size) { index ->
            val word = words[index]
            Text(
                text = "${word.englishWord}, ${word.spanishWord}, ${word.incorrectSpanishWords}, ${word.imagePath}, ${word.videoPath}, ${word.audioPath}",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
