import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.langio.useful.HeaderBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.langio.useful.Flippable
import com.example.langio.R
import com.example.langio.useful.rememberFlipController

//import androidx.compose.ui.tooling.preview.Preview
//
//@Composable
//fun test() {
//
//    var rotated by remember { mutableStateOf(false) }
//
//
//    val soundPath = "this/is/smaple/sound/path"
//    val videonPath = "this/is/smaple/video/path"
//
//    val frontWord = remember { "jengibre" }
//    val backWord = remember { "ginger" }
//
//    val frontSentence = remember { "Tu jengibre está en mi bolso." }
//    val backSentence = remember { "Your ginger is in my bag" }
//
//
//
//    val rotation by animateFloatAsState(
//        targetValue = if (rotated) 180f else 0f,
//        animationSpec = tween(500)
//    )
//
//    val animateFront by animateFloatAsState(
//        targetValue = if (!rotated) 1f else 0f,
//        animationSpec = tween(500)
//    )
//
//    val animateBack by animateFloatAsState(
//        targetValue = if (rotated) 1f else 0f,
//        animationSpec = tween(500)
//    )
//
//    val animateColor by animateColorAsState(
//        targetValue = if (rotated) Color.Gray else Color.LightGray,
//        animationSpec = tween(500)
//    )
//
//    Box(
//        Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxHeight(.7f)
//                .fillMaxWidth(.9f)
//                .graphicsLayer {
//                    rotationY = rotation
//                    cameraDistance = 8 * density
//                }
//                .clickable {
//                    rotated = !rotated
//                },
//            colors = CardDefaults.cardColors(
//                containerColor = animateColor)
//        )
//        {
//            Column(
//                Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//
//                Text(text = if (rotated) backWord else frontWord,
//                    modifier = Modifier
//                        .graphicsLayer {
//                            alpha = if (rotated) animateBack else animateFront
//                            rotationY = rotation
//                        })
//                Text(text = if (rotated) backSentence else frontSentence,
//                    modifier = Modifier
//                        .graphicsLayer {
//                            alpha = if (rotated) animateBack else animateFront
//                            rotationY = rotation
//                        })
//            }
//
//        }
//    }
//}
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun testPreview() {
//    test()
//}





@Composable
fun test(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { HeaderBar(modifier, showPfp = false, showLevel = true) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFF403E3E))
                .padding(paddingValues)
        ) {
//        flashcard()
            FCard()
        }
    }
}



@Composable
fun FCard()
{
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
                        painter = painterResource(R.drawable.cow), // Replace with your drawable
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(240.dp)
                            .padding(8.dp)
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
                                onClick = { /* Sound */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.chest), // Replace with your drawable
                                    contentDescription = "Sound",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { /* Video */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.cow), // Replace with your drawable
                                    contentDescription = "Sound",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
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
                        painter = painterResource(R.drawable.cow), // Replace with your drawable
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(240.dp)
                            .padding(8.dp)
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
                                onClick = { /* Sound */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.chest), // Replace with your drawable
                                    contentDescription = "Sound",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { /* Video */ }) {
                                Icon(
                                    painter = painterResource(R.drawable.cow), // Replace with your drawable
                                    contentDescription = "Sound",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = backSentence
                    )
                }
            }
        },
        flipController = rememberFlipController()
    )
}


@Composable
fun NextButton ()
{
    Button(
        onClick = { /* */},
        modifier = Modifier
            .fillMaxWidth(.9f)
            .height(100.dp)
    ) { }
}


@Preview(showBackground = true)
@Composable
fun testPreview() {
    test(rememberNavController() )

 }



