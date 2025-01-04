package com.example.langio.screens

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.langio.useful.Flippable
import com.example.langio.useful.HeaderBar
import com.example.langio.R
import com.example.langio.useful.rememberFlipController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.langio.controllers.GameController
import com.example.langio.models.WordInstance
import com.example.langio.useful.BackToLevelMenuButton
import com.example.langio.useful.FlippableState
import kotlin.random.Random


@Composable
fun FlashcardScreen(modifier: Modifier = Modifier) {
    val flashcards = remember { GameController.instance.currentScreenWordsToBeUsed ?: mutableListOf() }
    val currentFlashcardIndex = remember { mutableStateOf(Random.nextInt(flashcards.size)) }
    val wordInstance = remember { mutableStateOf(flashcards.removeAt(currentFlashcardIndex.value))}




    Scaffold(
        topBar = { HeaderBar(modifier, showLevel = true) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f),
                contentAlignment = Alignment.Center
            ) {
                if (flashcards.isNotEmpty()) {
                    FlashCard(wordInstance = wordInstance)
                    Log.e("FLASHCARD CHANGE", "IMHERE WITH ${wordInstance.value.englishWord}")
                } else {
                    Text(
                        text = "No flashcards available.",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (flashcards.isNotEmpty()) {

//                            TODO FLIP CARD

                            currentFlashcardIndex.value = Random.nextInt(flashcards.size)
                            wordInstance.value =  flashcards.removeAt(currentFlashcardIndex.value)
                        }
                        else {
                            GameController.instance.changeScreen(GameController.Screen.LEVEL_MENU)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(100.dp)
                ) {
                    Text(
                        text = if (flashcards.isNotEmpty()) "NEXT" else "FINISH",
                        fontSize = 45.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                BackToLevelMenuButton()
            }
        }
    }
}

@Composable
fun FlashCard(wordInstance: MutableState<WordInstance>) {
    val context = LocalContext.current
    val mp: MediaPlayer = MediaPlayer.create(context, R.raw.loud)

    val animateColor = Color.LightGray


    Flippable(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        frontSide = {
            Card(
                modifier = Modifier
                    .fillMaxSize(0.9f),
                colors = CardDefaults.cardColors(containerColor = animateColor)
            ) {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.cow), // Replace with actual image logic
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(25.dp)
                            .height(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = wordInstance.value.englishWord,
                            fontSize = 40.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                if (mp.isPlaying) {
                                    mp.pause()
                                    mp.seekTo(0)
                                }
                                mp.start()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.play_speaker),
                                contentDescription = "Audio",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                // Add your video play logic here
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.play_video),
                                contentDescription = "Video",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        fontSize = 20.sp,
                        color = Color(0xFF757575),
                        text = wordInstance.value.sampleOfUseEnglish,
                        lineHeight = 24.sp
                    )
                }
            }
        },
        backSide = {
            Card(
                modifier = Modifier
                    .fillMaxSize(0.9f),
                colors = CardDefaults.cardColors(containerColor = animateColor)
            ) {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.cow), // Replace with actual image logic
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(25.dp)
                            .height(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = wordInstance.value.spanishWord,
                            fontSize = 40.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                if (mp.isPlaying) {
                                    mp.pause()
                                    mp.seekTo(0)
                                }
                                mp.start()
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.play_speaker),
                                contentDescription = "Audio",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                // Add your video play logic here
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.play_video),
                                contentDescription = "Video",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        fontSize = 20.sp,
                        color = Color(0xFF757575),
                        text = wordInstance.value.sampleOfUseSpanish,
                        lineHeight = 24.sp
                    )
                }
            }
        },
        flipController = rememberFlipController()
    )
}






@Composable
fun NextButton() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                try {
                    GameController.instance.changeScreen(GameController.Screen.LEVEL_MENU)
                } catch (e: Exception) {
                    println("Navigation to Level Menu failed: ${e.message}")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(100.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(20.dp),
                fontSize = 45.sp,
                text = "NEXT"
            )
        }
    }
}