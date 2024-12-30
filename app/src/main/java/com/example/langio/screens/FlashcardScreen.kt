package com.example.langio.screens

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langio.useful.Flippable
import com.example.langio.useful.HeaderBar
import com.example.langio.R
import com.example.langio.useful.rememberFlipController
import androidx.compose.ui.platform.LocalContext




@Composable
fun FlashcardScreen (navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { HeaderBar(modifier, showPfp = false, showLevel = true, showExam = false) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
            FlashCard()
            NextButton(navController)
        }
    }
}

@Composable
fun FlashCard()
{
    val context = LocalContext.current
    val mp: MediaPlayer = MediaPlayer.create(context, R.raw.loud)

    val animateColor = Color.LightGray
    val frontWord = "jengibre"
    val frontSentence = "Tu jengibre está en mi bolso."
    val backWord = "ginger"
    val backSentence = "Your ginger is in my bag."
    Flippable(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
        frontSide =
        {
            Card(
                modifier = Modifier
                    .fillMaxSize(.9f),
                colors = CardDefaults.cardColors(
                    containerColor = animateColor)
            )
            {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.cow),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(25.dp)
                            .height(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Row (
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                    {
                        Box (
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                                .fillMaxHeight()
                        )
                        {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 40.sp,
                                text = frontWord)
                        }
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                        {
                            IconButton(
                                modifier = Modifier.fillMaxWidth(.5f),
                                onClick = {
                                    if (mp.isPlaying) {
                                        mp.pause()
                                        mp.seekTo(0)
                                    }
                                    mp.start()
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.play_speaker),
                                    contentDescription = "Sound",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { /* Video */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.play_video),
                                    contentDescription = "Cideo",
                                    tint = Color.Black
                                )
                                }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        fontSize = 20.sp,
                        color = Color(0xFF757575),
                        text = frontSentence
                    )
                }
            }
        },
        backSide =
        {
            Card(
                modifier = Modifier
                    .fillMaxSize(.9f),
                colors = CardDefaults.cardColors(
                    containerColor = animateColor)
            )
            {
                Column(
                    Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.cow),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(25.dp)
                            .height(200.dp)
//                            .fillMaxWidth(.9f)
                            .align(Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                    {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                                .fillMaxHeight()
                        )
                        {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 40.sp,
                                text = backWord
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        )
                        {
                            IconButton(
                                modifier = Modifier.fillMaxWidth(.5f),
                                onClick = {
                                    if (mp.isPlaying) {
                                        mp.pause()
                                        mp.seekTo(0)
                                    }
                                    mp.start()
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.play_speaker),
                                    contentDescription = "Sound",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { /* Video */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.play_video),
                                    contentDescription = "Video",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        fontSize = 20.sp,
                        color = Color(0xFF757575),
                        text = backSentence
                    )
                }
            }
        },
        flipController = rememberFlipController()
    )
}

@Composable
fun NextButton (navController: NavController)
{
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(
            onClick = {
//                navController.navigate("")
                TODO()
            },
            modifier = Modifier
                .fillMaxWidth(.85f)
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