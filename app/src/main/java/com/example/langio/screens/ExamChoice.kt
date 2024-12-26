package com.example.langio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.langio.R
import com.example.langio.useful.HeaderBar
import com.example.langio.useful.OneSidedHorizontalRoundedRectangle


@Composable
fun ExamChoice (navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { HeaderBar(modifier, showPfp = false, showLevel = true, showExam = true) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
            ExamCard()
            Answers()
            Hint()
        }
    }
}

@Composable
fun ExamCard ()
{

    val frontWord = "ginger"

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .fillMaxHeight(0.8f)
            .height(400.dp),
        contentAlignment = Alignment.Center
    )
    {
        Card(
            modifier = Modifier
                .fillMaxSize(.9f),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray)
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
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                    {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 40.sp,
                            text = frontWord)
                    }

                }

            }
        }
    }

}

@Composable
fun Answers()
{
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(.7f),
        contentAlignment = Alignment.Center
    )
    {
        Column (
            modifier = Modifier
                .fillMaxWidth(.95f)
                .fillMaxHeight(.8f)
        ){
            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(true),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8DEF8)),
                ) {
                    Text(
                        text = "jengibre",
                        color = Color(0xFF79747E),
                        fontSize = 25.sp,
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(false),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8DEF8)),
                    ) {
                    Text(
                        text = "jebgibere",
                        color = Color(0xFF79747E),
                        fontSize = 25.sp
                        )
                }
            }

            Row {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(true),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8DEF8)),

                    ) {
                    Text(
                        text = "jeingebre",
                        color = Color(0xFF79747E),
                        fontSize = 25.sp
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(80.dp),
                    shape = OneSidedHorizontalRoundedRectangle(false),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8DEF8)),

                    ) {
                    Text(
                        text = "jiengebere",
                        color = Color(0xFF79747E),
                        fontSize = 25.sp
                    )
                }
            }
        }
    }

}

@Composable
fun Hint()
{
    Box(
        modifier = Modifier.padding(20.dp).fillMaxWidth().fillMaxHeight(),
        contentAlignment = Alignment.CenterEnd

    )
    {
        Button(
            modifier = Modifier.fillMaxHeight().padding(horizontal = 10.dp),
            shape = RoundedCornerShape(5.dp),
            onClick = {
//                TODO
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8559A5)),

            ) {
            Icon(
                painter = painterResource(R.drawable.lightbulb),
                contentDescription = "Hint",
                tint = Color.Black
            )
            Text(
                text = "Hint",
                color = Color(0xFFF5F5F5),
                fontSize = 25.sp

            )
        }
    }
}