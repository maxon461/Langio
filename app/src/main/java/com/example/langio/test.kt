package com.example.langio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.langio.models.WordInstance
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import com.example.langio.screens.FlashCard
import com.example.langio.useful.rememberFlipController

class MainActivity2 : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val fcard1 = WordInstance("dog", "dogo", listOf("saa", "sp", "sss", "sasas", "sadafa", "fgad"), "example", "ejemplo")
            val fcard2 = WordInstance("cat", "gato", listOf("saa", "sp", "sss", "sasas", "sadafa", "fgad"), "example", "ejemplo")

            val fcardWord = remember { mutableStateOf(fcard1) }
            var shouldFlip by remember { mutableStateOf(false) }
            val flipController = rememberFlipController()

            FlashCard(fcardWord, flipController)
            Button(
                onClick = {
                    flipController.flip()
                    if (fcardWord.value == fcard1)
                        fcardWord.value = fcard2
                    else
                        fcardWord.value = fcard1
                }
            ) {
                Text("CLICKME")
            }
        }
    }
}



@Composable
fun IconWithTextInside() {
    Box(
        modifier = Modifier
            .size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = "Example Icon",
            modifier = Modifier.size(50.dp)
        )

        Text(
            text = "3",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}






