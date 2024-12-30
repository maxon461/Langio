
package com.example.langio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.langio.useful.OneSidedHorizontalRoundedRectangle

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    shape = OneSidedHorizontalRoundedRectangle(true),
                    onClick = {}
                ) {}
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    shape = OneSidedHorizontalRoundedRectangle(false),
                    onClick = {}
                ) {
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    VideoPlayerTheme {
//        VideoPlayerScreen()
//    }
}


